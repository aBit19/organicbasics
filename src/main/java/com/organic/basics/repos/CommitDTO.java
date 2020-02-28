package com.organic.basics.repos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
class CommitDTO {
  @JsonProperty(value = "author.name")
  private String name;
  private String message;
  @JsonProperty(value = "author.date")
  private String date;

  CommitDTO(String name, String message, String date) {
    this.name = Objects.requireNonNull(name);
    this.message = Objects.requireNonNull(message);
    this.date = Objects.requireNonNull(date);
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
