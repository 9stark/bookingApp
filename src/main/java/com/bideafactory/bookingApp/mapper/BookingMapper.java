package com.bideafactory.bookingApp.mapper;

import com.bideafactory.bookingApp.model.Booking;
import com.bideafactory.bookingApp.dto.BookRequest;

public interface BookingMapper {
    Booking toBooking(BookRequest bookRequest);
}