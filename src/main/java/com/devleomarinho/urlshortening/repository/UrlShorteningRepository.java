package com.devleomarinho.urlshortening.repository;

import com.devleomarinho.urlshortening.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlShorteningRepository extends JpaRepository<UrlEntity, Long> {

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
