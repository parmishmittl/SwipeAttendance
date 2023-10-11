package com.attendance.swipe.SwipeService.dao;

import com.attendance.swipe.SwipeService.Employee.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDaoService {


    private static List<Employee> employees = new ArrayList<>();
    public static int employees_Count = 0;

    static {
        employees.add(new Employee("Adam", ++employees_Count));
        employees.add(new Employee("Eve", ++employees_Count));
        employees.add(new Employee("Jim", ++employees_Count));
    }


    public List<Employee> findAll() {

        return employees;
    }
}