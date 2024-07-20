package com.bideafactory.bookingApp.mapper;

import com.bideafactory.bookingApp.model.Booking;
import com.bideafactory.bookingApp.dto.BookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public Booking toBooking(BookRequest bookRequest) {
        if (bookRequest == null) {
            return null;
        }

        Booking booking = new Booking();
        booking.setId(bookRequest.getId());
        booking.setName(bookRequest.getName());
        booking.setLastname(bookRequest.getLastname());
        booking.setAge(bookRequest.getAge());
        booking.setPhoneNumber(bookRequest.getPhoneNumber());
        booking.setStartDate(bookRequest.getStartDate());
        booking.setEndDate(bookRequest.getEndDate());
        booking.setHouseId(bookRequest.getHouseId());
        booking.setDiscountCode(bookRequest.getDiscountCode());

        return booking;
    }
}