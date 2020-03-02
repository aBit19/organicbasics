package com.organic.basics.webservice;

import org.springframework.web.client.RestTemplate;

public class WebServiceFactory {

  private WebServiceFactory() {}

  public static <T> WebService<T> getInstance(Class<T> type) {
    return new WebServiceImpl<>(type, new RestTemplate());
  }
}
