package com.example.springscratcherjdbc;

import java.time.Instant;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableJdbcRepositories
public class Config {
  @Bean
  public AuditorAware<Instant> instantAuditorAware() {
    return () -> Optional.of(Instant.now());
  }
}
