package com.bideafactory.bookingApp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateBooking_ValidRequest() throws Exception {
        String requestBody = """
                {
                    "id": "123456789",
                    "name": "John",
                    "lastname": "Doe",
                    "age": 25,
                    "phoneNumber": "1234567890",
                    "startDate": "2024-01-01",
                    "endDate": "2024-12-31",
                    "houseId": "H123456"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateBooking_InvalidRequest() throws Exception {
        String requestBody = """
                {
                    "userId": "",
                    "houseId": "213132",
                    "discountCode": "D0542A23"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_MissingRequiredField() throws Exception {
        String requestBody = """
        {
            "name": "John",
            "lastname": "Doe",
            "age": 25,
            "phoneNumber": "1234567890",
            "startDate": "2024-01-01",
            "endDate": "2024-12-31",
            "houseId": "H123456",
            "discountCode": "DISC2024"
        }
        """; // Missing "id"

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_InvalidDateFormat() throws Exception {
        String requestBody = """
        {
            "id": "123456789",
            "name": "John",
            "lastname": "Doe",
            "age": 25,
            "phoneNumber": "1234567890",
            "startDate": "2024-15-01",
            "endDate": "2024-12-31",
            "houseId": "H123456",
            "discountCode": "DISC2024"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testCreateBooking_InvalidDiscountCode() throws Exception {
        String requestBody = """
        {
            "id": "123456789",
            "name": "John",
            "lastname": "Doe",
            "age": 25,
            "phoneNumber": "1234567890",
            "startDate": "2024-01-01",
            "endDate": "2024-12-31",
            "houseId": "H123456",
            "discountCode": "INVALIDCODE"
        }
        """;
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_InvalidPhoneNumber() throws Exception {
        String requestBody = """
        {
            "id": "123456789",
            "name": "John",
            "lastname": "Doe",
            "age": 25,
            "phoneNumber": "12345",
            "startDate": "2024-01-01",
            "endDate": "2024-12-31",
            "houseId": "H123456",
            "discountCode": "DISC2024"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBooking_InvalidAge() throws Exception {
        String requestBody = """
        {
            "id": "123456789",
            "name": "John",
            "lastname": "Doe",
            "age": -5,
            "phoneNumber": "1234567890",
            "startDate": "2024-01-01",
            "endDate": "2024-12-31",
            "houseId": "H123456",
            "discountCode": "DISC2024"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

}