package com.organic.basics.webservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public T getResponse(String url, Map<String, String> parameters) throws ResponseException {
    UriBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
    for (Map.Entry<String, String> param2Value : parameters.entrySet()) {
      uriBuilder.queryParam(param2Value.getKey(), param2Value.getValue());
    }
    URI uri = uriBuilder.build();

    try {
      ResponseEntity<T> forEntity = restOperations.getForEntity(uri, type);
      if (forEntity.getStatusCode().isError()) {
        throw new ResponseException(forEntity.getStatusCodeValue(), forEntity.getStatusCode().getReasonPhrase());
      }
      return forEntity.getBody();
    } catch (Exception e) {
      throw new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
  }

}
