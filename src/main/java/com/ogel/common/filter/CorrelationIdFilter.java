package com.ogel.common.filter;

import com.ogel.common.correlation.CorrelationIdHolder;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_MDC = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String correlationId = request.getHeader(CustomHeaders.CORRELATION_ID) != null ?
                    request.getHeader(CustomHeaders.CORRELATION_ID) : UUID.randomUUID().toString();
            CorrelationIdHolder.set(correlationId);
            MDC.put(CORRELATION_ID_MDC, correlationId);
            response.addHeader(CustomHeaders.CORRELATION_ID, correlationId);
            filterChain.doFilter(request, response);
        } finally {
            CorrelationIdHolder.clear();
            MDC.clear();
        }
    }
}