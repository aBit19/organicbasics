package com.organic.basics.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

class AuthorizationFilter extends BasicAuthenticationFilter {
  private final UserDetailsService userDetailsService;

  AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String header = request.getHeader(ConstantProvider.AUTHORIZATION_HEADER);
    if (header == null || !header.startsWith(AuthTokenProvider.TYPE)) {
      chain.doFilter(request, response);
      return;
    }
    SecurityContextHolder.getContext().setAuthentication(getAuthentication(header));
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String header) {
    String token = header.replace(AuthTokenProvider.TYPE, "");
    String usernameFromToken = AuthTokenProvider.getUsernameFromToken(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
    return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), Collections.emptyList());
  }
}

