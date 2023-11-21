package com.distributedsystems.studentmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    private int statusCode;
    private String message;
    private String timeStamp;
}
