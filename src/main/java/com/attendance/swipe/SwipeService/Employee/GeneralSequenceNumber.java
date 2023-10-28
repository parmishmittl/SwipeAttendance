package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GeneralSequenceNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long number;
}

