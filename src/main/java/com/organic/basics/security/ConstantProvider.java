package com.organic.basics.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class ConstantProvider {
  private ConstantProvider() {
  }

  static final String AUTHORIZATION_HEADER = "Authorization";

  static PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
