package com.organic.basics.repos;

import com.organic.basics.webservice.WebService;
import com.organic.basics.webservice.WebServiceFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RepoService {
  private static final String GITHUB_API = "https://api.github.com/";

  List<CommitDTO> getCommits() {
    WebService<CommitDTO[]> webService = getWebService(CommitDTO[].class);
    return Arrays.asList(webService.getResponse(GITHUB_API));
  }

  Set<String> getLanguages() {
    WebService<String[]> webService = getWebService(String[].class);
    return Set.of(webService.getResponse(GITHUB_API));
  }

  List<RepoDTO> getRecentRepos(int limit) {
    Map<String, String> parameters = Map.of("direction", "desc");
    WebService<RepoDTO[]> webService = getWebService(RepoDTO[].class);
    RepoDTO[] response = webService.getResponse(GITHUB_API, parameters);
    return response.length <= limit
            ? Arrays.asList(response)
            : Arrays.asList(Arrays.copyOf(response, limit));
  }

  <T> WebService<T> getWebService(Class<T> classType) {
    return WebServiceFactory.getInstance(classType);
  }
}
