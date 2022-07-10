package com.vpestov.app.controller;

import com.vpestov.app.service.NasaService;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class NasaController {
    private final NasaService nasaService;

    private ApplicationContext context;

    public NasaController(NasaService nasaService, ApplicationContext context) {
        this.nasaService = nasaService;
        this.context = context;
    }

    @GetMapping({"/pictures/{sol}/largest"})
    public ResponseEntity<String> getLargestImage(@PathVariable String sol) {
        URI imgUrl = URI.create(nasaService.getLargestImage(sol));
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).location(imgUrl).build();
    }
}
