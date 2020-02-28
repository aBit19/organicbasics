package com.organic.basics.webservice;

import org.springframework.web.client.RestTemplate;

import java.util.Map;

class WebServiceImpl<T> implements WebService<T> {

  private final RestTemplate restTemplate;
  private final Class<T> type;

  WebServiceImpl(Class<T> type) {
    this.restTemplate = new RestTemplate();
    this.type = type;
  }

  @Override
  public T getResponse(String url, Map<String, String> parameters) {
    return restTemplate.getForObject(url, type, parameters);
  }

}
