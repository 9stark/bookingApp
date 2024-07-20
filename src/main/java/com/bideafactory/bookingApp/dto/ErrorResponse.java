package com.bideafactory.bookingApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ErrorResponse {
  @JsonProperty("statusCode")
  private int statusCode;

  @JsonProperty("error")
  private String error;

  @JsonProperty("message")
  private String message;



}

