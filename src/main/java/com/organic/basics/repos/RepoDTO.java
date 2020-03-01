package com.organic.basics.repos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
class RepoDTO {
  private String name;
  @JsonProperty(value = "html_url")
  private String url;

  RepoDTO() {
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  @Override
  public String toString() {
    return "{" +
            "name='" + name + '\'' +
            ", url='" + url + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof RepoDTO)) return false;

    RepoDTO repoDTO = (RepoDTO) o;
    return Objects.equals(name, repoDTO.name) &&
            Objects.equals(url, repoDTO.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, url);
  }
}
