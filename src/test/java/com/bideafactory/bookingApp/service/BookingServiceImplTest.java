package com.bideafactory.bookingApp.service;

import com.bideafactory.bookingApp.dto.DiscountRequest;
import com.bideafactory.bookingApp.exception.BookingException;
import com.bideafactory.bookingApp.mapper.BookingMapper;
import com.bideafactory.bookingApp.model.Booking;
import com.bideafactory.bookingApp.repository.BookingRepository;
import com.bideafactory.bookingApp.dto.BookRequest;
import com.bideafactory.bookingApp.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.refEq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingServiceImplTest {

    @MockBean
    private DiscountService discountService;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingServiceImpl(bookingRepository, discountService, bookingMapper);
    }

    @Test
    public void testValidDiscountCode() {
        when(discountService.validateDiscountCode(any(DiscountRequest.class))).thenReturn(true);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setId("123456789");
        bookRequest.setHouseId("H123456");
        bookRequest.setDiscountCode("IS_VALID");

        Booking booking = new Booking();
        booking.setId("123456789");
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Method to test
        assertDoesNotThrow(() -> bookingService.createBooking(bookRequest));

        DiscountRequest expectedRequest = new DiscountRequest("123456789", "H123456", "IS_VALID");
        verify(discountService).validateDiscountCode(refEq(expectedRequest));
    }

    @Test
    public void testInvalidDiscountCode() {
        when(discountService.validateDiscountCode(any(DiscountRequest.class))).thenReturn(false);

        BookRequest bookRequest = new BookRequest();
        bookRequest.setId("123456789");
        bookRequest.setHouseId("H123456");
        bookRequest.setDiscountCode("INVALID_CODE");

        // Method to test
        BookingException thrown = assertThrows(BookingException.class, () -> bookingService.createBooking(bookRequest));

        assertEquals("Invalid discount", thrown.getMessage());

        DiscountRequest expectedRequest = new DiscountRequest("123456789", "H123456", "INVALID_CODE");
        verify(discountService).validateDiscountCode(refEq(expectedRequest));
    }
}