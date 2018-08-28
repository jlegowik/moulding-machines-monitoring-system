package com.ogel.common.correlation.interceptor;

import com.ogel.common.correlation.CorrelationIdHolder;
import com.ogel.common.filter.CustomHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/*
    This class is not used yet but can be helpful in RestTemplate
 */
public class CorrelationIdInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (headers.get(CustomHeaders.CORRELATION_ID) == null
                && CorrelationIdHolder.get() != null) {
            headers.add(CustomHeaders.CORRELATION_ID, CorrelationIdHolder.get());
        }

        return execution.execute(request, body);
    }
}