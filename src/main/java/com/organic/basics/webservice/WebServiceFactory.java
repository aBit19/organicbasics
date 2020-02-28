package com.organic.basics.webservice;

public class WebServiceFactory {

  private WebServiceFactory() {}

  public static <T> WebService<T> getInstance(Class<T> type) {
    return new WebServiceImpl<>(type);
  }
}
