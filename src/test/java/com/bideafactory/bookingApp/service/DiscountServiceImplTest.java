package com.bideafactory.bookingApp.service;import com.bideafactory.bookingApp.dto.DiscountRequest;
import com.bideafactory.bookingApp.dto.DiscountResponse;
import com.bideafactory.bookingApp.service.impl.DiscountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DiscountServiceImplTest {

    @Autowired
    private DiscountServiceImpl discountService;

    @MockBean
    private RestTemplate restTemplate;

    @Value("${discount.api.url}")
    private String discountApiUrl;

    @Test
    public void testValidateDiscountCode_Success() {
        // Arrange
        DiscountRequest request = new DiscountRequest("user123", "house123", "DISCOUNT10");
        DiscountResponse response = new DiscountResponse( "house123", "DISCOUNT10", "discountId", "user123", true);
        ResponseEntity<DiscountResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(DiscountResponse.class)))
                .thenReturn(responseEntity);

        // Act
        boolean result = discountService.validateDiscountCode(request);

        // Assert
        assertTrue(result, "Expected discount code to be valid");
    }

    @Test
    public void testValidateDiscountCode_Failure() {
        // Arrange
        DiscountRequest request = new DiscountRequest("user123", "house123", "DISCOUNT10");
        DiscountResponse response = new DiscountResponse( "house123", "DISCOUNT10", "discountId", "user123", false);
        ResponseEntity<DiscountResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(DiscountResponse.class)))
                .thenReturn(responseEntity);

        // Act
        boolean result = discountService.validateDiscountCode(request);

        // Assert
        assertFalse(result, "Expected discount code to be invalid");
    }

    @Test
    public void testValidateDiscountCode_RestClientException() {
        // Arrange
        DiscountRequest request = new DiscountRequest("user123", "house123", "DISCOUNT10");

        // Mock the RestTemplate to throw RestClientException
        when(restTemplate.postForEntity(eq(discountApiUrl), any(HttpEntity.class), eq(DiscountResponse.class)))
                .thenThrow(new RestClientException("Error"));

        // Act & Assert
        assertFalse(discountService.validateDiscountCode(request));
    }

    @Test
    public void testRecover_WithRestClientException() {
        // Arrange
        DiscountRequest request = new DiscountRequest("user123", "house123", "DISCOUNT10");
        RestClientException exception = new RestClientException("Error");

        // Act
        boolean result = discountService.recover(exception, request);

        // Assert
        assertFalse(result, "Expected recovery to return false");
    }
}
