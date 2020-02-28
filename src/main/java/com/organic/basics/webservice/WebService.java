package com.organic.basics.webservice;

import java.util.Collections;
import java.util.Map;

public interface WebService<T> {

  public T getResponse(String URL, Map<String, String> parameters);

  default public T getResponse(String URL) {
    return getResponse(URL, Collections.emptyMap());
  };

}
