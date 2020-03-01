package com.organic.basics.webservice;

public class ResponseException extends Exception {
  private final int httpStatus;

  ResponseException(int httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public int getStatusCode() {
    return httpStatus;
  }

}
