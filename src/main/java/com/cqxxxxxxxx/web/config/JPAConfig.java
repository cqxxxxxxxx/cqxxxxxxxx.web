package com.cqxxxxxxxx.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by BG307435 on 2017/11/20.
 */
@Configuration
@EnableJpaRepositories("com.cqxxxxxxxx.web.dao")   //Set up Spring to create proxy instances for those interfaces.
@EnableTransactionManagement   //Enables Spring's annotation-driven transaction management capability
@ComponentScan
public class JPAConfig {
}
