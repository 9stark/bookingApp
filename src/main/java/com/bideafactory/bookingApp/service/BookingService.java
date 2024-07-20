package com.bideafactory.bookingApp.service;

import com.bideafactory.bookingApp.dto.BookRequest;
import com.bideafactory.bookingApp.dto.BookResponse;

public interface BookingService {
    BookResponse createBooking(BookRequest bookRequestDTO);
}