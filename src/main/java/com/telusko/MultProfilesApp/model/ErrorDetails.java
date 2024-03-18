package com.telusko.MultProfilesApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorDetails {

    private String errorMessage;
    private LocalDateTime dateTime;

}
