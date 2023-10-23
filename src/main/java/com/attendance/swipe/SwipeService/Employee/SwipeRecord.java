package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="SwipeRecord")
@Table(name = "SwipeRecord", indexes = {
        @Index(name = "idx_empId_date", columnList = "empId, date")})
public class SwipeRecord {

    private LocalDateTime swipeIn;
    private LocalDateTime swipeOut;

@EmbeddedId
    private SwipeRecordKey swipeRecordKey;
    public Integer getEmpId()
    {
        return swipeRecordKey.getEmpId();
    }
    public SwipeRecordKey getSwipeRecordKey() {
        return swipeRecordKey;
    }

    public void setSwipeRecordKey(SwipeRecordKey swipeRecordKey) {
        this.swipeRecordKey = swipeRecordKey;
    }

    public LocalDateTime getSwipeIn() {
        return swipeIn;
    }

    public void setSwipeIn(LocalDateTime swipeIn) {
        this.swipeIn = swipeIn;
    }

    public LocalDateTime getSwipeOut() {
        return swipeOut;
    }

    public void setSwipeOut(LocalDateTime swipeOut) {
        this.swipeOut = swipeOut;
    }
}
