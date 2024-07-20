package com.bideafactory.bookingApp.service.impl;

import com.bideafactory.bookingApp.dto.DiscountRequest;
import com.bideafactory.bookingApp.exception.BookingException;
import com.bideafactory.bookingApp.mapper.BookingMapper;
import com.bideafactory.bookingApp.model.Booking;
import com.bideafactory.bookingApp.repository.BookingRepository;
import com.bideafactory.bookingApp.dto.BookRequest;
import com.bideafactory.bookingApp.dto.BookResponse;
import com.bideafactory.bookingApp.service.BookingService;
import com.bideafactory.bookingApp.service.DiscountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;

    private final DiscountService discountService;

    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, DiscountService discountService, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.discountService = discountService;
        this.bookingMapper = bookingMapper;
    }


    public BookResponse createBooking(BookRequest bookRequest) throws BookingException {
        logger.debug("Validating discount code for user: {} and house: {}", bookRequest.getId(), bookRequest.getHouseId());

        // Check if a booking already exists for the given ID
        if (bookingRepository.existsById(bookRequest.getId())) {
            throw new BookingException(400, "Conflict", "A booking already exists with this ID");
        }

        // Check if discountCode is provided
        if (bookRequest.getDiscountCode() != null && !bookRequest.getDiscountCode().isEmpty()) {
            logger.debug("Validating discount code for user: {} and house: {}", bookRequest.getId(), bookRequest.getHouseId());
            DiscountRequest discountRequest =new DiscountRequest(bookRequest.getId(), bookRequest.getHouseId(), bookRequest.getDiscountCode());
            boolean isDiscountValid = discountService.validateDiscountCode(discountRequest);
            if (!isDiscountValid) {
                throw new BookingException(400, "Conflict", "Invalid discount");
            }
        }

        Booking booking = bookingMapper.toBooking(bookRequest);
        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking saved successfully with ID: {}", savedBooking.getId());
        return new BookResponse(HttpStatus.OK.value(), "Book Accepted");
    }



}