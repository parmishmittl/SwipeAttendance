package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="SwipeRecord")
@Table(name = "SwipeRecord", indexes = {
        @Index(name = "idx_empId_date", columnList = "empId, date")})
public class SwipeRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime swipeIn;
    private LocalDateTime swipeOut;
  //  @Column(name="date",nullable = false)
    //private LocalDate date;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
