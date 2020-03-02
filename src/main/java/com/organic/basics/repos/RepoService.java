package com.organic.basics.repos;

import com.organic.basics.webservice.ResponseException;
import com.organic.basics.webservice.WebServiceFactory;

import java.util.Arrays;
import java.util.Collections;
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
  private static final String REPO_COMMITS = GITHUB_API + "/repos/Shopify/%s/commits";

  List<CommitDTO> getCommitsOfSince(String repositoryName, String since) throws ResponseException {
    Objects.requireNonNull(repositoryName);
    Objects.requireNonNull(since);
    Map<String, String> parameters = singletonMap("since", since);

    CommitDTO[] commitDTOS = get(String.format(REPO_COMMITS, repositoryName), CommitDTO[].class, parameters);
    return Arrays.asList(commitDTOS);
  }

  Set<String> getLanguagesOf(String repositoryName) throws ResponseException {
    Objects.requireNonNull(repositoryName);
    String languagesURL = String.format(REPO_LANGUAGE, repositoryName);
    Map<?, ?> languagesResponse = get(languagesURL, Map.class, Collections.emptyMap());
    return languagesResponse.keySet().stream().map(Object::toString).collect(toSet());
  }

  List<RepoDTO> getRecentRepos(int limit) throws ResponseException {
    Map<String, String> parameters = singletonMap("direction", "desc");
    RepoDTO[] repoDTOS = get(ORGS_REPO_ENDPOINT, RepoDTO[].class, parameters);
    return repoDTOS.length <= limit ? Arrays.asList(repoDTOS) : Arrays.asList(Arrays.copyOf(repoDTOS, limit));
  }

  <T> T get(String URL, Class<T> classType, Map<String, String> parameters) throws ResponseException {
    return WebServiceFactory.getInstance(classType)
            .getResponse(URL, parameters);
  }

}
