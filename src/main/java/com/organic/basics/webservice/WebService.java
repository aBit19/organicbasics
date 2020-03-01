package com.organic.basics.webservice;

import java.util.Map;

public interface WebService<T> {
  T getResponse(String URL, Map<String, String> parameters) throws ResponseException;
}
