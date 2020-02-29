package com.organic.basics.webservice;

import org.springframework.boot.web.client.RestTemplateBuilder;

public class WebServiceFactory {

  private static final String TOKEN = "bbf0cb12afd3c2a2fb997a61188408ce1277dd30";
  private static final String USERNAME = "aBit19";

  private WebServiceFactory() {}

  public static <T> WebService<T> getInstance(Class<T> type) {
    return new WebServiceImpl<>(type, new RestTemplateBuilder().basicAuthentication(USERNAME, TOKEN).build());
  }
}
