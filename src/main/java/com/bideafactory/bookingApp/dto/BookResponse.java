package com.bideafactory.bookingApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class BookResponse {
    @JsonProperty("code")
    private int  code;

    @JsonProperty("message")
    private String message;


}
