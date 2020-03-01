package com.organic.basics.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;

class AuthorizationTokenProvider {

  private final String username;

  private static final int TOKEN_VALIDITY = 24 * 60 * 60 * 1000;
  private static final String SECRET = "jwtGrokonezSecretKey";
  static final String TYPE = "Bearer";

  private static final Logger logger = LoggerFactory.getLogger(AuthorizationTokenProvider.class);

  AuthorizationTokenProvider(String username) {
    this.username = Objects.requireNonNull(username);
  }

  String getType() {
    return TYPE;
  }

  String generateToken() {
    Date now = new Date();
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + TOKEN_VALIDITY))
            .signWith(SignatureAlgorithm.HS256, SECRET)
            .compact();
  }

  static String getUsernameFromToken(String token) {
    try {
      return Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token)
              .getBody().getSubject();
    } catch (Exception e) {
      logger.error(e.getMessage());
      return null;
    }

  }
}
