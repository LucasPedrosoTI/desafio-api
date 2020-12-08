package com.gft.desafioapi.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@SuppressWarnings("deprecation")
@Service
public class ApiUsageService {

  private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

  public Bucket resolveBucket(String token) {
    return cache.computeIfAbsent(token, this::newBucket);
  }

  public Bucket newBucket(String token) {
    Bandwidth bandwidth = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
    return Bucket4j.builder().addLimit(bandwidth).build();
  }
}
