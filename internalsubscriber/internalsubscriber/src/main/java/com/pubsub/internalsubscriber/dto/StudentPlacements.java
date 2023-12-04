package com.pubsub.internalsubscriber.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StudentPlacements {
    private Placements placements;
    private List<Student> studentResult;
}
