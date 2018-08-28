package com.ogel.monitoring.system.config.web;

import com.ogel.common.filter.CorrelationIdFilterRegistrationBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean loggingContextFilterRegistration() {
        return new CorrelationIdFilterRegistrationBean();
    }

}
