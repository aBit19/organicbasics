package com.organic.basics.security;

class SignInResponse {

  private String token;
  private String type;

  SignInResponse(AuthorizationTokenProvider authorizationTokenProvider) {
    this.token = authorizationTokenProvider.generateToken();
    this.type = authorizationTokenProvider.getType();
  }

  public String getToken() {
    return token;
  }

  public String getType() {
    return type;
  }
}
