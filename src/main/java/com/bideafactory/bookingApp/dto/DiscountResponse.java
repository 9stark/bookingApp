package com.bideafactory.bookingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscountResponse {
    private String houseId;
    private String discountCode;
    private String id;
    private String userId;
    private boolean status;

}
