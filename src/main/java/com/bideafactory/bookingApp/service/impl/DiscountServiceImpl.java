package com.bideafactory.bookingApp.service.impl;

import com.bideafactory.bookingApp.dto.DiscountRequest;
import com.bideafactory.bookingApp.dto.DiscountResponse;
import com.bideafactory.bookingApp.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscountServiceImpl implements DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Value("${discount.api.url}")
    private String discountApiUrl;

    @Value("${retry.maxAttempts}")
    private String retry;

    private final RestTemplate restTemplate;

    public DiscountServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            value = { RestClientException.class },
            maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.backoffDelay}")
    )
    @Override
    public boolean validateDiscountCode(DiscountRequest discountRequest) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Map<String, String>> entity = createHttpEntity(discountRequest, headers);
        logger.info("Sending request to validate discount code: {}", discountRequest);
        ResponseEntity<DiscountResponse> response = sendRequest(entity);
        return handleResponse(response);
    }


    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private HttpEntity<Map<String, String>> createHttpEntity(DiscountRequest discountRequest, HttpHeaders headers) {
        Map<String, String> request = new HashMap<>();
        request.put("userId", discountRequest.getUserId());
        request.put("houseId", discountRequest.getHouseId());
        request.put("discountCode", discountRequest.getDiscountCode());
        return new HttpEntity<>(request, headers);
    }

    private ResponseEntity<DiscountResponse> sendRequest(HttpEntity<Map<String, String>> entity) {
        return restTemplate.postForEntity(discountApiUrl, entity, DiscountResponse.class);
    }

    private boolean handleResponse(ResponseEntity<DiscountResponse> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            DiscountResponse body = response.getBody();
            if (body != null) {
                return body.isStatus();
            }
        }
        logger.warn("Received non-OK status code: {} with body: {}", response.getStatusCode(), response.getBody());
        return false;
    }

    @Recover
    public boolean recover(RestClientException e, DiscountRequest discountRequest) {
        logger.info("Failed to validate discount code after retries: {},request: {}", retry, discountRequest);
        return false;
    }
}