package com.organic.basics.repos;

import com.organic.basics.webservice.WebService;
import com.organic.basics.webservice.WebServiceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toSet;

class RepoService {
  private static final String GITHUB_API = "https://api.github.com";
  private static final String ORGS_REPO_ENDPOINT = GITHUB_API + "/orgs/Shopify/repos";
  private static final String REPO_LANGUAGE = GITHUB_API + "/repos/Shopify/%s/languages";
  private static final String REPO_COMMITS = (GITHUB_API + "/repos/Shopify/%s/commits");

  List<CommitDTO> getCommitsOfSince(String repositoryName, String since) {
    Objects.requireNonNull(repositoryName);
    Objects.requireNonNull(since);
    Map<String, String> parameters = singletonMap("since", since);

    WebService<CommitDTO[]> webService = getWebService(CommitDTO[].class);
    CommitDTO[] commits = webService
            .getResponse(String.format(REPO_COMMITS, repositoryName), parameters);
    return Arrays.asList(commits);
  }

  Set<String> getLanguagesOf(String repositoryName) {
    Objects.requireNonNull(repositoryName);
    WebService<Map> webService = getWebService(Map.class);
    String languagesURL = String.format(REPO_LANGUAGE, repositoryName);

    @SuppressWarnings("It will succed since we call toString() method in the key set")
    Set<String> languages = (Set<String>) webService
            .getResponse(languagesURL)
            .keySet()
            .stream()
            .map(Object::toString)
            .collect(toSet());
    return languages;
  }

  List<RepoDTO> getRecentRepos(int limit) {
    Map<String, String> parameters = singletonMap("direction", "desc");
    WebService<RepoDTO[]> webService = getWebService(RepoDTO[].class);
    RepoDTO[] response = webService.getResponse(ORGS_REPO_ENDPOINT, parameters);
    return response.length <= limit
            ? Arrays.asList(response)
            : Arrays.asList(Arrays.copyOf(response, limit));
  }

  <T> WebService<T> getWebService(Class<T> classType) {
    return WebServiceFactory.getInstance(classType);
  }
}
