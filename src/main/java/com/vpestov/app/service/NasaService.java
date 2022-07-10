package com.vpestov.app.service;

import com.vpestov.app.domain.NasaResponse;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class NasaService {

    private static final String API_KEY = "DEMO_KEY";
    private static final String REQUEST_URL_TEMPLATE = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol={sol}&api_key={api_key}";

    private ApplicationContext context;

    public NasaService(ApplicationContext context) {
        this.context = context;
    }

    @Cacheable("largest")
    public String getLargestImage(String sol) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        CloseableHttpClient client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        factory.setHttpClient(client);
        restTemplate.setRequestFactory(factory);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("sol", sol);
        urlParams.put("api_key", API_KEY);
        UriComponentsBuilder url = UriComponentsBuilder.fromUriString(REQUEST_URL_TEMPLATE);
        NasaResponse response = restTemplate.getForObject(url.buildAndExpand(urlParams).toUri(), NasaResponse.class);

        assert response != null;

        var result = response.getPhotos().parallelStream()
                .map((photo) -> Pair.of(photo.getImgSrc(), restTemplate.headForHeaders(URI.create(photo.getImgSrc()))))
                .max(Comparator.comparing(pair -> pair.getRight().getContentLength())).orElseThrow();
        return result.getLeft();
    }
}
