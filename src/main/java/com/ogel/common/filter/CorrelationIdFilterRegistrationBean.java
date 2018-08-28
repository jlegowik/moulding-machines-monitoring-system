package com.ogel.common.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

public class CorrelationIdFilterRegistrationBean extends FilterRegistrationBean {

    private static final int ORDER = 1;
    private static final String URL_PATTERN = "/*";

    public CorrelationIdFilterRegistrationBean() {
        setFilter(new CorrelationIdFilter());
        addUrlPatterns(URL_PATTERN);
        setOrder(ORDER);
    }
}