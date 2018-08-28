package com.ogel.monitoring.system.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogel.common.correlation.interceptor.CorrelationIdInterceptor;
import com.ogel.common.rest.versioning.ApiVersionHandlerMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    private final String versionPrefix;
    private final ObjectMapper objectMapper;

    public WebConfiguration(@Value("${ogel.api.version-prefix:v}") String versionPrefix, ObjectMapper objectMapper) {
        this.versionPrefix = versionPrefix;
        this.objectMapper = objectMapper;
    }

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping(versionPrefix);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api-docs/**");
        registry.addMapping("/v1/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setWriteAcceptCharset(false);
        converters.add(stringConverter);
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    // ---------------------------------------------------------------------------------------------------------
    /*
        For now I don't need to call external API so RestTemplate is not used but I wanted to show how to use 'CorrelationIdInterceptor'
     */
    @Bean
    public CorrelationIdInterceptor correlationIdInterceptor() {
        return new CorrelationIdInterceptor();
    }

    @Bean
    @Primary
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(correlationIdInterceptor()));
        return restTemplate;
    }
    // ---------------------------------------------------------------------------------------------------------

}
