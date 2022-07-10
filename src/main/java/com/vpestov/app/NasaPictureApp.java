package com.vpestov.app;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@Log4j2
public class NasaPictureApp {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Scheduled(cron = "0 0 * * * *")
    @CacheEvict(value = "largest", allEntries = true)
    public void clearCache() {
        log.info("cleaning cache");
    }

    public static void main(String[] args) {
        SpringApplication.run(NasaPictureApp.class, args);
    }
}
