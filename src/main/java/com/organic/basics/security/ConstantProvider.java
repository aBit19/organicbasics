package com.organic.basics.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class ConstantProvider {
  private ConstantProvider() {
  }

  static PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


}
