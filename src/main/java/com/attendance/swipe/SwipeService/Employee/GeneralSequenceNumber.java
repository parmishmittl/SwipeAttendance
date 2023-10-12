package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.*;

@Entity
public class GeneralSequenceNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
}

