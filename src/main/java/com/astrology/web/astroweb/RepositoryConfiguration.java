package com.astrology.web.astroweb;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.astrology.web.astroweb.domain"})
@EnableJpaRepositories(basePackages = {"com.astrology.web.astroweb.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}
