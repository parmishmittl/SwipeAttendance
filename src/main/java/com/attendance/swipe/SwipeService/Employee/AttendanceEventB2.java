package com.attendance.swipe.SwipeService.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class AttendanceEventB2 {


    @Id
    @GeneratedValue
    private Integer empId;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    private long totalHours;
    private String stateEmployee;

    public AttendanceEventB2(Integer empId, LocalDate date, long totalHours, String stateEmployee) {
        this.empId = empId;
        this.date = date;
        this.totalHours = totalHours;
        this.stateEmployee = stateEmployee;
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

    public long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(long totalHours) {
        this.totalHours = totalHours;
    }

    public String getStateEmployee() {
        return stateEmployee;
    }

    public void setStateEmployee(String stateEmployee) {
        this.stateEmployee = stateEmployee;
    }

    @Override
    public String toString() {
        return "AttendanceEvent{" +
                "empId=" + empId +
                ", date=" + date +
                ", totalHours=" + totalHours +
                ", stateEmployee='" + stateEmployee + '\'' +
                '}';
    }
}
