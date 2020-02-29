package com.organic.basics.repos;

import com.organic.basics.webservice.WebService;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepoServiceTest {

  private RepoService repoService;
  private static final String DEFAULT_TIME = "1970-01-01";

  @Before
  public void setUp() {
    repoService = spy(new RepoService());
  }

  @Test(expected = NullPointerException.class)
  public void getLanguagesOf_given_null_repository_then_NPE() {
    repoService.getLanguagesOf(null);
  }

  @Test
  @SuppressWarnings("mocks webservice")
  public void getLanguagesOf_given_non_null_repo_then_call_correct_api() {
    String url = "https://api.github.com/repos/Shopify/repository/languages";
    String repository = "repository";

    WebService<Map> mock = mock(WebService.class);
    doReturn(mock).when(repoService).getWebService(Map.class);
    when(mock.getResponse(url)).thenReturn(Collections.emptyMap());

    repoService.getLanguagesOf(repository);

    verify(mock, times(1))
            .getResponse(url);
  }


  @Test(expected = NullPointerException.class)
  public void getCommitsOfSince_given_null_since_then_NPE() {
    repoService.getCommitsOfSince("repo", null);
  }

  @Test
  @SuppressWarnings("mock web service")
  public void getCommitsOfSince_given_repo_and_since_date_then_use_correct_param() {
    String since = "since";
    String repoName = "repository";
    String url = "https://api.github.com/repos/Shopify/repository/commits";
    Map<String, String> expectedParam = singletonMap("since", since);

    WebService<CommitDTO[]> webService = mock(WebService.class) ;
    doReturn(webService).when(repoService).getWebService(CommitDTO[].class);
    when(webService.getResponse(url, expectedParam)).thenReturn(new CommitDTO[]{});

    repoService.getCommitsOfSince(repoName, since);

    verify(webService, times(1))
            .getResponse(url, expectedParam);
  }
}