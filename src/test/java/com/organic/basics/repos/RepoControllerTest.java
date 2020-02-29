package com.organic.basics.repos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepoControllerTest {

  private RepoController repoController;
  private RepoService repoService;

  @Before
  public void setUp() {
    repoService = mock(RepoService.class);
    repoController = spy(new RepoController(repoService));
  }

  @Test
  public void languages_given_specific_repo_then_call_repo_service() {
    String repository = "repository";
    when(repoService.getLanguagesOf(repository)).thenReturn(Collections.singleton("Java"));

    Map<String, Set<String>> languages = repoController.languages(repository);

    assertTrue(languages.containsKey(repository));
    Assert.assertEquals(1, languages.get(repository).size());
    assertTrue(languages.get(repository).contains("Java"));
  }

  @Test
  public void language_given_no_repository_then_get_languages_of_all_repos() {
    String repository = "shipping";
    String languageOfRepo = "ruby";
    RepoDTO repoDTO = mock(RepoDTO.class);
    when(repoDTO.getName()).thenReturn(repository);
    when(repoService.getRecentRepos(50)).thenReturn(singletonList(repoDTO));
    when(repoService.getLanguagesOf(repository)).thenReturn(singleton(languageOfRepo));


    Map<String, Set<String>> languages = repoController.languages(null);


    assertTrue(languages.containsKey(repository));
    Assert.assertEquals(1, languages.get(repository).size());
    assertTrue(languages.get(repository).contains(languageOfRepo));
  }

  @Test
  public void commits_given_repository_then_delegate_to_repo_service() {
    String repoName = "gomail";
    String since = "1970-2-2";
    CommitDTO commitDTO = mock(CommitDTO.class);
    when(commitDTO.getMessage()).thenReturn("message");
    when(commitDTO.getDate()).thenReturn("date");
    when(commitDTO.getName()).thenReturn("name");

    when(repoService.getCommitsOfSince(repoName, since))
            .thenReturn(singletonList(commitDTO));

    Map<String, List<CommitDTO>> commits = repoController.commits(repoName, since);

    assertTrue(commits.containsKey(repoName));
    assertEquals(1, commits.get(repoName).size());
    assertEquals(commitDTO, commits.get(repoName).get(0));
  }

  @Test
  public void commits_given_no_repository_then_get_commits_for_all_repos() {
    String since = "1970-2-2";
    String repoName = "reponame";

    doReturn(singletonList(repoName))
            .when(repoController).getRepoNames();


    Map<String, List<CommitDTO>> commits =
            repoController.commits(null, since);

    verify(repoService, times(1))
            .getCommitsOfSince(repoName, since);



  }
}