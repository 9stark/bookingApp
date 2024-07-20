package com.bideafactory.bookingApp.service;

import com.bideafactory.bookingApp.dto.DiscountRequest;

public interface DiscountService {
    boolean validateDiscountCode(DiscountRequest request);
}
