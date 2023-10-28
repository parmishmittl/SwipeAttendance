package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class SwipeRecordKey implements Serializable {
    @Column(name = "empId", nullable = false)
    private Integer empId;
    @Column(name = "date", nullable = false)
    private LocalDate date;

    public SwipeRecordKey() {
    }

    public SwipeRecordKey(Integer empId, LocalDate date) {
        this.empId = empId;
        this.date = date;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
