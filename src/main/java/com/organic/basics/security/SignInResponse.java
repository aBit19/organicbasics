package com.organic.basics.security;

class SignInResponse {

  private String token;
  private String type;

  SignInResponse(String token, String type) {
    this.token = token;
    this.type = type;
  }

  public String getToken() {
    return token;
  }

  public String getType() {
    return type;
  }
}
