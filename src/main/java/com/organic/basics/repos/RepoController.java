package com.organic.basics.repos;

import com.organic.basics.webservice.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/shopify/repos")
public class RepoController {
  private final RepoService repoService;

  private static final String REPOSITORY_PARAM = "repository";
  private static final String DEFAULT_SINCE = "2017-01-01";
  private static final int REPO_LIMIT = 50;

  RepoController(RepoService repoService) {
    this.repoService = repoService;
  }

  RepoController() {
    this.repoService = new RepoService();
  }

  @GetMapping("/commits")
  public ResponseEntity<Map<String, List<CommitDTO>>> commits(
          @RequestParam(value = REPOSITORY_PARAM, required = false) String repository,
          @RequestParam(value = "since", defaultValue = DEFAULT_SINCE) String since) {
    return handleResponse(getForRepo(repository, repoName -> repoService.getCommitsOfSince(repoName, since)));
  }

  @GetMapping("/languages")
  public ResponseEntity<Map<String, Set<String>>> languages(
          @RequestParam(value = REPOSITORY_PARAM, required = false) String repository) {
    return handleResponse(getForRepo(repository, repoService::getLanguagesOf));
  }

  @GetMapping("/all")
  public ResponseEntity<List<RepoDTO>> repos(
          @RequestParam(value = "limit", defaultValue = "50") int limit) {
    return handleResponse(repoService.getRecentRepos(limit));
  }

  private <T> Optional<Map<String, T>> getForRepo(String repository, Function<String, Optional<T>> getFoRepo) {
    if (repository != null) {
      Optional<T> response = getFoRepo.apply(repository);
      return response.map(r -> singletonMap(repository, r));
    }
    Optional<List<String>> repoNames = getRepoNames();
    if (!repoNames.isPresent()) {
      return Optional.empty();
    } else {
      Map<String, T> result = new HashMap<>();
      for (String repoName : repoNames.get()) {
        Optional<T> response = getFoRepo.apply(repoName);
        if (response.isPresent()) {
          result.put(repoName, response.get());
        } else {
          return Optional.empty();
        }
      }
      return Optional.of(result);
    }
  }

  Optional<List<String>> getRepoNames() {
    Optional<List<RepoDTO>> recentRepos = repoService.getRecentRepos(REPO_LIMIT);
    return recentRepos.map(r -> r.stream().map(RepoDTO::getName).collect(toList()));
  }

  private <T> ResponseEntity<T> handleResponse(Optional<T> response) {
    if (response.isPresent()) {
      return ok(response.get());
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
