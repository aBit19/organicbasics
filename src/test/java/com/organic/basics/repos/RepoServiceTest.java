package com.organic.basics.repos;

import com.organic.basics.webservice.ResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepoServiceTest {

  private RepoService repoService;

  @Before
  public void setUp() {
    repoService = spy(new RepoService());
  }

  @Test(expected = NullPointerException.class)
  public void getLanguagesOf_given_null_repository_then_NPE() throws ResponseException {
    repoService.getLanguagesOf(null);
  }

  @Test
  @SuppressWarnings("mocks webservice")
  public void getLanguagesOf_given_non_null_repo_then_call_correct_api() throws ResponseException {
    String url = "https://api.github.com/repos/Shopify/repository/languages";
    String repository = "repository";

    doReturn(Collections.singletonMap("java", 1))
            .when(repoService).get(url, Map.class, Collections.emptyMap());

    Set<String> languagesOf = repoService.getLanguagesOf(repository);

    Assert.assertTrue(languagesOf.contains("java"));
  }


  @Test(expected = NullPointerException.class)
  public void getCommitsOfSince_given_null_since_then_NPE() throws ResponseException {
    repoService.getCommitsOfSince("repo", null);
  }

  @Test
  @SuppressWarnings("mock web service")
  public void getCommitsOfSince_given_repo_and_since_date_then_use_correct_param() throws ResponseException {
    String since = "since";
    String repoName = "repository";
    String url = "https://api.github.com/repos/Shopify/repository/commits";
    Map<String, String> expectedParam = singletonMap("since", since);

    doReturn(new CommitDTO[]{}).when(repoService).get(url, CommitDTO[].class, expectedParam);

    repoService.getCommitsOfSince(repoName, since);

    verify(repoService, times(1))
            .get(url, CommitDTO[].class, expectedParam);
  }
}