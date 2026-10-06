package com.nbcamp.coffeeordersystem.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// @CreatedDate, @LastModifiedDate가 동작하도록 JPA Auditing을 켠다
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}
