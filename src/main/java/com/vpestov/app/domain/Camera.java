package com.vpestov.app.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Camera {

    private int id;

    private String name;

    @JsonProperty(value = "rover_id")
    private int roverId;

    @JsonProperty(value = "full_name")
    private String fullName;
}
