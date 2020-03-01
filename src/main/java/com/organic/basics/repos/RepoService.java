package com.organic.basics.repos;

import com.organic.basics.webservice.ResponseException;
import com.organic.basics.webservice.WebServiceFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toSet;

class RepoService {

  private static final String GITHUB_API = "https://api.github.com";
  private static final String ORGS_REPO_ENDPOINT = GITHUB_API + "/orgs/Shopify/repos";
  private static final String REPO_LANGUAGE = GITHUB_API + "/repos/Shopify/%s/languages";
  private static final String REPO_COMMITS = GITHUB_API + "/repos/Shopify/%s/commits";

  Optional<List<CommitDTO>> getCommitsOfSince(String repositoryName, String since) {
    Objects.requireNonNull(repositoryName);
    Objects.requireNonNull(since);
    Map<String, String> parameters = singletonMap("since", since);

    Optional<CommitDTO[]> optionalCommitDTOS = get(String.format(REPO_COMMITS, repositoryName), CommitDTO[].class, parameters);
    return optionalCommitDTOS.map(Arrays::asList);
  }

  Optional<Set<String>> getLanguagesOf(String repositoryName) {
    Objects.requireNonNull(repositoryName);
    String languagesURL = String.format(REPO_LANGUAGE, repositoryName);
    Optional<Map> languagesResponse = get(languagesURL, Map.class, Collections.emptyMap());

    if (languagesResponse.isPresent()) {
      @SuppressWarnings("The language is the key to the returned map")
      Set<String> languages = (Set<String>) languagesResponse.get()
              .keySet().stream().map(Object::toString).collect(toSet());
      return Optional.of(languages);
    } else {
      return Optional.empty();
    }
  }

  Optional<List<RepoDTO>> getRecentRepos(int limit) {
    Map<String, String> parameters = singletonMap("direction", "desc");
    Optional<RepoDTO[]> repoDTOS = get(ORGS_REPO_ENDPOINT, RepoDTO[].class, parameters);
    return repoDTOS.map(res -> res.length <= limit ? Arrays.asList(res) : Arrays.asList(Arrays.copyOf(res, limit)));
  }

  <T> Optional<T> get(String URL, Class<T> classType, Map<String, String> parameters) {
    try {
      return Optional.of(WebServiceFactory.getInstance(classType).getResponse(URL, parameters));
    } catch (ResponseException e) {
      return Optional.empty();
    }
  }

}
