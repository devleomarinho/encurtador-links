package com.devleomarinho.urlshortening.services;

import com.devleomarinho.urlshortening.dto.UrlShorteningResponse;
import com.devleomarinho.urlshortening.entity.UrlEntity;
import com.devleomarinho.urlshortening.repository.UrlShorteningRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShorteningService {

    private final UrlShorteningRepository repository;

    public UrlShorteningService(UrlShorteningRepository repository){
        this.repository = repository;
    }

    public UrlEntity shortenUrl(String originalUrl){
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl(originalUrl);
        urlEntity.setShortUrl(generateShortUrl());
        urlEntity.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        return repository.save(urlEntity);
    }

    public Optional<UrlEntity> findByShortUrl(String shortUrl){
        return repository.findByShortUrl(shortUrl);
    }

    private String generateShortUrl(){
     String shortUrl;
     do{
         shortUrl = RandomStringUtils.randomAlphanumeric(5,10);
     }while(repository.findByShortUrl(shortUrl).isPresent());
     return shortUrl;
    }
}
