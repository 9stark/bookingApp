package com.bideafactory.bookingApp.dto;
import com.bideafactory.bookingApp.exception.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequest {
    @NotNull(message = "Required property: ID")
    @Size(min = 9, max = 10, message = "ID must be between 9 and 10 characters")
    private String id;

    @NotNull(message = "Required property: Name")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Required property: Lastname")
    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname;

    @NotNull(message = "Required property: Age")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must be at most 100")
    private Integer age;

    @NotNull(message = "Required property: Phone number")
    @Size(min = 9, max = 20, message = "Phone number must be between 9 and 20 characters")
    private String phoneNumber;

    @NotNull(message = "Required property: Start date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @NotNull(message = "Required property: End date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @NotNull(message = "Required property: House ID")
    @Size(min = 6, max = 15, message = "House ID must be between 6 and 15 characters")
    private String houseId;

    @Size(min = 8, max = 8, message = "Discount code must be exactly 8 characters")
    private String discountCode;

}
