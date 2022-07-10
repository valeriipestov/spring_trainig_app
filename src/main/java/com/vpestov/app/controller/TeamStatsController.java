package com.vpestov.app.controller;

import com.vpestov.app.domain.StastResponse;
import com.vpestov.app.domain.Team;
import com.vpestov.app.domain.TeamMember;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;
import java.util.Comparator;

@RestController
public class TeamStatsController {

    private static final String url = "http://93.175.204.87:8080/training/stats";

    private final RestTemplate restTemplate;

    public TeamStatsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/people/max")
    public String getMaxTrainingTimePerson() {
        var response = restTemplate.getForObject(URI.create(url), StastResponse.class);
        assert response != null;
        var maxTrainingPerson = response.getTeams().stream()
                .map(Team::getTeamMembers)
                .flatMap(Collection::stream)
                .max(Comparator.comparing(TeamMember::getMinutesPerTrainingDay));
        return maxTrainingPerson.toString();
    }


}
