package com.attendance.swipe.SwipeService;


import com.attendance.swipe.SwipeService.Employee.Employee;
import com.attendance.swipe.SwipeService.Employee.SwipeRecord;
import com.attendance.swipe.SwipeService.Employee.SwipeRecordKey;
import com.attendance.swipe.SwipeService.dao.EmployeeDaoService;
import com.attendance.swipe.SwipeService.jpa.EmployeeRepository;
import com.attendance.swipe.SwipeService.jpa.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path="/employees")
public class SwipeController {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private SwipeRepository swipeRepository;
    @Autowired
    EmployeeDaoService employeeDaoService;


    public SwipeController(EmployeeRepository repository, EmployeeDaoService employeeDaoService) {
        this.repository = repository;
        this.employeeDaoService = employeeDaoService;
    }


    @GetMapping("/getAllEmployees")
    public Iterable<Employee> getEmployees()
    {
        return repository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<Object> addNewEmployee (@RequestParam  String name
            ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Employee employee = new Employee();
        employee.setEmpName(name);
        repository.save(employee);
        String responseMessage = "Employee added successfully: " + employee.getEmpName() + " (ID: " + employee.getEmpId() + ")";
        CustomResponse response = new CustomResponse(201, responseMessage);
        return ResponseEntity.ok(response);

    }
    @DeleteMapping("{id}/deleteEmployee")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id)
    {
        Optional<Employee> employee=repository.findById(id);
        repository.delete(employee.get()); //deleteById
        String responseMessage = "Deleted successfully: " + employee.get().getEmpName() + " (ID: " + employee.get().getEmpId() + ")";
        CustomResponse response = new CustomResponse(200, responseMessage);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("{id}/deleteSwipeRecord")
    public ResponseEntity<Object> deleteSwipeRecord(@RequestBody SwipeRecordKey swipeRecordKey)
    {
        Optional<SwipeRecord> swipeRecord=swipeRepository.findById(swipeRecordKey);
        swipeRepository.delete(swipeRecord.get());
        String responseMessage = "Swipe Record deleted successfully: for date" + swipeRecord.get().getSwipeRecordKey().getDate() + " (ID: " + swipeRecord.get().getEmpId() + ")";
        CustomResponse response = new CustomResponse(200, responseMessage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("{id}/swipeIn")
    public ResponseEntity<Object> swipeIn(@PathVariable Integer id)
    {
        Optional<Employee> employee=repository.findById(id);
        if(employee.isEmpty())
        {
            //throw new UserNotFoundException("User Not Found RUntimeExceptionid:"+id);
        }
        int controller_id=employee.get().getEmpId();
        SwipeRecord swipeRecord=new SwipeRecord();
        SwipeRecordKey swipeRecordKey=new SwipeRecordKey();
        swipeRecord.setId((int) Math.random());
        swipeRecordKey.setEmpId(id);
        swipeRecordKey.setDate(LocalDate.now());

        swipeRecord.setSwipeIn(LocalDateTime.now());
        swipeRecord.setSwipeRecordKey(swipeRecordKey);
        swipeRepository.save(swipeRecord);

        String responseMessage = "Swipe In Time added successfully: " + swipeRecord.getSwipeIn() + " (ID: " + swipeRecord.getEmpId() + ")";
        CustomResponse response = new CustomResponse(201, responseMessage);
        return ResponseEntity.ok(response);

    }
    @PostMapping("{id}/swipeOut")
    public ResponseEntity<Object> swipeOut(@PathVariable Integer id)
    {
        Optional<Employee> employee=repository.findById(id);
        if(employee.isEmpty())
        {
            //throw new UserNotFoundException("User Not Found RUntimeExceptionid:"+id);
        }

        int controller_id=employee.get().getEmpId();
        SwipeRecord swipeRecord=new SwipeRecord();
        SwipeRecordKey swipeRecordKey=new SwipeRecordKey();
        swipeRecord.setId((int) Math.random());
        swipeRecordKey.setEmpId(id);
        swipeRecordKey.setDate(LocalDate.now());
        swipeRecord.setSwipeOut(LocalDateTime.now());
        swipeRecord.setSwipeRecordKey(swipeRecordKey);
        swipeRepository.save(swipeRecord);
        String responseMessage = "Swipe Out Time added successfully: " + swipeRecord.getSwipeOut() + " (ID: " + swipeRecord.getEmpId() + ")";
        CustomResponse response = new CustomResponse(201, responseMessage);
        return ResponseEntity.ok(response);

    }



}
