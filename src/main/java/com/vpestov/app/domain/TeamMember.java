package com.vpestov.app.domain;

import lombok.Data;

@Data
public class TeamMember {

    private String firstName;

    private String lastName;

    private Integer trainingDaysPerWeek;

    private Integer minutesPerTrainingDay;
}
