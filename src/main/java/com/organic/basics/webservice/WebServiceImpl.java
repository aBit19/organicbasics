package com.organic.basics.webservice;

import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

class WebServiceImpl<T> implements WebService<T> {

  private final RestOperations restOperations;
  private final Class<T> type;

  WebServiceImpl(Class<T> type, RestOperations restOperations) {
    this.type = type;
    this.restOperations = restOperations;
  }

  @Override
  public T getResponse(String url, Map<String, String> parameters) {
    UriBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
    for (Map.Entry<String, String> param2Value: parameters.entrySet()) {
      uriBuilder.queryParam(param2Value.getKey(), param2Value.getValue());
    }
    URI uri = uriBuilder.build();

    return restOperations.getForObject(uri, type);
  }

}
