package com.vpestov.app.domain;

import lombok.Data;

import java.util.List;

@Data
public class Team {

    private String team;

    private List<TeamMember> teamMembers;

    private Integer teamMembersCount;

    private Double teamAverageDaysPerWeek;

    private Double teamAverageMinutesPerTrainingDay;
}
