package com.devleomarinho.urlshortening.controller;

import com.devleomarinho.urlshortening.dto.UrlShorteningRequest;
import com.devleomarinho.urlshortening.dto.UrlShorteningResponse;
import com.devleomarinho.urlshortening.entity.UrlEntity;
import com.devleomarinho.urlshortening.repository.UrlShorteningRepository;
import com.devleomarinho.urlshortening.services.UrlShorteningService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<UrlShorteningResponse> shortenUrl(@RequestBody UrlShorteningRequest request, HttpServletRequest servletRequest){
        UrlEntity urlEntity = urlShorteningService.shortenUrl(request.url());
        String redirectUrl = servletRequest.getRequestURL().toString().replace("shorten-url", urlEntity.getShortUrl());
        return ResponseEntity.ok(new UrlShorteningResponse(redirectUrl));
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl){

        Optional<UrlEntity> optionalShortenedUrl = urlShorteningService.findByShortUrl(shortUrl);

        if (optionalShortenedUrl.isPresent()) {
            UrlEntity urlEntity = optionalShortenedUrl.get();
            if (urlEntity.getExpiresAt().isAfter(LocalDateTime.now())) {
                return ResponseEntity.status(302).header("Location", urlEntity.getOriginalUrl()).build();
            } else {
                return ResponseEntity.status(410).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }}
}
