package com.organic.basics.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessControlServiceTest {

  private AccessControlService accessControlService;

  @Before
  public void setUp() throws Exception {
    accessControlService = new AccessControlService();
  }

  @Test
  public void register_given_user_name_does_not_exist_when_register_then_true() {
    SignDTO signUpDTO = mock(SignDTO.class);
    when(signUpDTO.getPassword()).thenReturn("password");
    when(signUpDTO.getUsername()).thenReturn("username");

    boolean registerUser = accessControlService.registerUser(signUpDTO);
    assertTrue(registerUser);
  }

  @Test
  public void register_given_email_exists_when_register_false() {
    SignDTO signUpDTO = mock(SignDTO.class);
    when(signUpDTO.getPassword()).thenReturn("password");
    when(signUpDTO.getUsername()).thenReturn("username");

    accessControlService.registerUser(signUpDTO);

    boolean registerUser = accessControlService.registerUser(signUpDTO);

    assertFalse(registerUser);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_given_user_name_do_not_exist_then_UNFE() {

    accessControlService.loadUserByUsername("test");
  }

  @Test
  public void loadUserByUsername_given_user_exist_then_get_user() {
    SignDTO signUpDTO = mock(SignDTO.class);when(signUpDTO.getPassword()).thenReturn("password");
    when(signUpDTO.getUsername()).thenReturn("username");

    accessControlService.registerUser(signUpDTO);

    UserDetails userDetails = accessControlService.loadUserByUsername("username");
    assertEquals("username", userDetails.getUsername());
  }
}