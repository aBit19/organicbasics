package com.organic.basics.repos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
class RepoDTO {
  private final String name;
  @JsonProperty(value = "html_url")
  private final String url;

  RepoDTO(String name, String url) {
    this.name = Objects.requireNonNull(name);
    this.url = Objects.requireNonNull(url);
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }
}
