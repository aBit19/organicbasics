package com.organic.basics.repos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
class CommitDTO {

  private String name;
  private String message;
  private String date;

  CommitDTO() {}

  @JsonProperty("commit")
  private void unpackNested(Map<String, Object> commit) {
    this.message = commit.getOrDefault("message", "").toString();
    @SuppressWarnings("Author is a nested object in the reponse")
    Map<String, Object> author = (Map<String, Object>)commit.get("author");
    this.name = author.getOrDefault("name", "").toString();
    this.date = author.getOrDefault("date", "").toString();
  }

  public String getName() {
    return name;
  }
  public String getMessage() {
    return message;
  }

  public String getDate() {
    return date;
  }

}
