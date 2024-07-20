package com.bideafactory.bookingApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book")
public class Booking {
    @Id
    @Column(length = 10)
    private String id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(nullable = false)
    private Integer age;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length = 15, nullable = false)
    private String houseId;

    @Column(length = 8)
    private String discountCode;
}