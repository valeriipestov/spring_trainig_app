package com.vpestov.app.domain;

import lombok.Data;

import java.util.List;

@Data
public class NasaResponse {
    private List<Photo> photos;
}
