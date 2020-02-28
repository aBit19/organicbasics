package com.organic.basics.repos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/shopify")
public class RepoController {
    private final RepoService repoService;

    RepoController(RepoService repoService) {
        this.repoService = repoService;
    }

    RepoController() {
        this.repoService = new RepoService();
    }

    @GetMapping("/commits")
    public List<CommitDTO> commits() {
        return repoService.getCommits();
    }

    @GetMapping("/languages")
    public Set<String> languages() {
        return repoService.getLanguages();
    }

    @GetMapping("/repos")
    public List<RepoDTO> repos(
            @RequestParam(value = "limit", defaultValue = "50") int limit) {

        return repoService.getRecentRepos(limit);
    }

}
