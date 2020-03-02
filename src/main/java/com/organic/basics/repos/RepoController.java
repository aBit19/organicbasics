package com.organic.basics.repos;

import com.organic.basics.webservice.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    return getForRepo(repository, repoName -> repoService.getCommitsOfSince(repoName, since));
  }

  @GetMapping("/languages")
  public ResponseEntity<Map<String, Set<String>>> languages(
          @RequestParam(value = REPOSITORY_PARAM, required = false) String repository) {
    return getForRepo(repository, repoService::getLanguagesOf);
  }

  @GetMapping("/all")
  public ResponseEntity<List<RepoDTO>> repos(
          @RequestParam(value = "limit", defaultValue = "50") int limit) {
    try {
      return ok(repoService.getRecentRepos(limit));
    } catch (ResponseException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  private <T> ResponseEntity<Map<String, T>> getForRepo(String repository, FunctionResponse<String, T> getFoRepo) {
    try {
      if (repository != null) {
        T response = getFoRepo.apply(repository);
        return ok(singletonMap(repository, response));
      }
      List<String> repoNames = getRepoNames();
      Map<String, T> result = new HashMap<>();
      for (String repoName : repoNames) {
        result.put(repoName, getFoRepo.apply(repoName));
      }
      return ok(result);
    } catch (ResponseException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  List<String> getRepoNames() throws ResponseException {
    List<RepoDTO> recentRepos = repoService.getRecentRepos(REPO_LIMIT);
    return recentRepos.stream().map(RepoDTO::getName).collect(toList());
  }

  @FunctionalInterface
  private interface FunctionResponse<T, R> {
    R apply(T t) throws ResponseException;
  }

}
