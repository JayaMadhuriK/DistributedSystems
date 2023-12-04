package com.pubsub.internalsubscriber.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Student {
    private Long universityRollNumber;
    private String firstName;
    private String lastName;
    private String contactDetails;
    private Course course;
    private Year year;
    private double cgpa;
}
