package com.attendance.swipe.SwipeService;


import com.attendance.swipe.SwipeService.Employee.AttendanceEvent;
import com.attendance.swipe.SwipeService.Employee.Employee;
import com.attendance.swipe.SwipeService.Employee.SwipeRecord;
import com.attendance.swipe.SwipeService.Employee.SwipeRecordKey;
import com.attendance.swipe.SwipeService.Kafka.Producer;
import com.attendance.swipe.SwipeService.jpa.EmployeeRepository;
import com.attendance.swipe.SwipeService.jpa.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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
    Producer producer;
    public SwipeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/getAllEmployees")
    public Iterable<Employee> getEmployees()
    {
        return repository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity<Object> addNewEmployee (@RequestParam  String name) {
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

    @DeleteMapping("/deleteSwipeRecord")
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
        LocalDate now = LocalDate.now();
        SwipeRecordKey swipeRecordKey=new SwipeRecordKey();
        swipeRecordKey.setDate(now);
        swipeRecordKey.setEmpId(id);
        Optional<SwipeRecord> swipeRecordOptional = swipeRepository.findById(swipeRecordKey);
        if(swipeRecordOptional.isPresent())
        {
            String responseMessage = "Swipe In Time already exists";
            CustomResponse response = new CustomResponse(201, responseMessage);
            return ResponseEntity.ok(response);
        }
        else
        {
            SwipeRecord swipeRecord=new SwipeRecord();
            //swipeRecord.setId((int) Math.random()+213);
            swipeRecord.setSwipeIn(LocalDateTime.now());
            swipeRecord.setSwipeRecordKey(swipeRecordKey);
            swipeRepository.save(swipeRecord);
            String responseMessage = "Swipe In Time added successfully: " + swipeRecord.getSwipeIn() + " (ID: " + swipeRecord.getEmpId() + ")";
            CustomResponse response = new CustomResponse(201, responseMessage);
            return ResponseEntity.ok(response);
        }

    }
    @PostMapping("{id}/swipeOut")
    public ResponseEntity<Object> swipeOut(@PathVariable Integer id)
    {
        LocalDate now = LocalDate.now();
        SwipeRecordKey swipeRecordKey=new SwipeRecordKey();
        swipeRecordKey.setDate(now);
        swipeRecordKey.setEmpId(id);
        Optional<SwipeRecord> swipeRecordOptional = swipeRepository.findById(swipeRecordKey);
        if(swipeRecordOptional.isEmpty())
        {
            ResponseEntity.badRequest();
        }
       LocalDateTime firstSwipeInTime=swipeRecordOptional.get().getSwipeIn();
        SwipeRecord swipeRecord=new SwipeRecord();
        swipeRecord.setSwipeIn(firstSwipeInTime);
        swipeRecord.setSwipeOut(LocalDateTime.now());
        swipeRecord.setSwipeRecordKey(swipeRecordKey);
        swipeRepository.save(swipeRecord);
        String responseMessage = "Swipe Out Time added successfully: " + swipeRecord.getSwipeOut() + " (ID: " + swipeRecord.getEmpId() + ")";
        CustomResponse response = new CustomResponse(201, responseMessage);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/calculateAttendance")
    public ResponseEntity<Object> calculateAttendance(@RequestBody SwipeRecordKey swipeRecordKey)
    {
        Optional<SwipeRecord> swipeRecordOptional = swipeRepository.findById(swipeRecordKey);
        if(swipeRecordOptional.isEmpty())
        {
            ResponseEntity.badRequest();
        }
        LocalDateTime firstSwipeInTime=swipeRecordOptional.get().getSwipeIn();
        LocalDateTime lastSwipeOutTime=swipeRecordOptional.get().getSwipeOut();
        Duration inOffice = Duration.between(firstSwipeInTime, lastSwipeOutTime);
        long inOfficeHours=  inOffice.toHours();
        String responseMessage=new String() ;
        if(inOfficeHours>8)
        {
            responseMessage="present";
        }
        else
        {
            if(inOfficeHours<4)
            {
                responseMessage="absent";
            }
            else
            {
                responseMessage="Half Day";
            }
}
        CustomResponse response = new CustomResponse(200, responseMessage);
        AttendanceEvent attendanceEvent= AttendanceEvent.newBuilder().setDate(swipeRecordKey.getDate().toString()).setEmpId(swipeRecordKey.getEmpId()).
                setStateEmployee(responseMessage).setTotalHours(inOfficeHours).build();

        producer.sendMessage(attendanceEvent);
        return ResponseEntity.ok(response);
    }
}
