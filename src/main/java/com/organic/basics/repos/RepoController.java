package com.organic.basics.repos;

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
    public Map<String, List<CommitDTO>> commits(
            @RequestParam(value = REPOSITORY_PARAM, required = false) String repository,
            @RequestParam(value = "since", defaultValue = DEFAULT_SINCE) String since) {
        if (repository != null) {
            return singletonMap(repository, repoService.getCommitsOfSince(repository, since));
        } else {
            return getCommitsOfRepos(since);
        }
    }

    private Map<String, List<CommitDTO>> getCommitsOfRepos(String since) {
        List<String> repoNames = getRepoNames();
        Map<String,  List<CommitDTO>> commits = new HashMap<>();
        for (String repoName : repoNames) {
            commits.put(repoName, repoService.getCommitsOfSince(repoName, since));
        }
        return commits;
    }

    @GetMapping("/languages")
    public Map<String, Set<String>> languages(
            @RequestParam(value = REPOSITORY_PARAM, required = false) String repository) {
      if (repository != null) {
          return singletonMap(repository, repoService.getLanguagesOf(repository));
      } else {
          return getLanguagesOfRepos();
      }
    }

    private Map<String, Set<String>> getLanguagesOfRepos() {
        List<String> repoNames = getRepoNames();
        Map<String, Set<String>> languages = new HashMap<>();
        for (String repoName : repoNames) {
            languages.put(repoName, repoService.getLanguagesOf(repoName));
        }
        return languages;
    }

    List<String> getRepoNames() {
        return repos(REPO_LIMIT)
                .stream()
                .map(RepoDTO::getName)
                .collect(toList());
    }

    @GetMapping("/all")
    public List<RepoDTO> repos(
            @RequestParam(value = "limit", defaultValue = "50") int limit) {
        return repoService.getRecentRepos(limit);
    }

}
