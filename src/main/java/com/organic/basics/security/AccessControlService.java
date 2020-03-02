package com.organic.basics.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.organic.basics.security.ConstantProvider.getPasswordEncoder;

@Service
class AccessControlService implements UserDetailsService {

  @Autowired
  private AuthenticationManager authenticationManager;

  private static final Map<String, UserDetails> userDetailsByUsername = new ConcurrentHashMap<>();

  boolean registerUser(SignDTO signUpDTO) {
    if (userDetailsByUsername.containsKey(signUpDTO.getUsername())) {
      return false;
    } else {
      userDetailsByUsername.put(signUpDTO.getUsername(), new User(signUpDTO));
      return true;
    }
  }

  SignInResponse authenticate(SignDTO signDTO) {
    UserDetails userDetails = authenticateUser(signDTO);
    return new SignInResponse(new AuthTokenProvider(userDetails.getUsername()).generateToken(), AuthTokenProvider.TYPE);
  }

  private UserDetails authenticateUser(SignDTO signDTO) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(signDTO.getUsername(), signDTO.getPassword());
    Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return (UserDetails) authentication.getPrincipal();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username != null && userDetailsByUsername.containsKey(username)) {
      return userDetailsByUsername.get(username);
    }
    throw new UsernameNotFoundException(String.format("Username %s not found", username));
  }

  static class User implements UserDetails {
    private final String userName;
    private final String password;

    private User(SignDTO signUpDTO) {
      this.userName = signUpDTO.getUsername();
      this.password = getPasswordEncoder().encode(signUpDTO.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.emptyList();
    }

    @Override
    public String getPassword() {
      return password;
    }

    @Override
    public String getUsername() {
      return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }
}
