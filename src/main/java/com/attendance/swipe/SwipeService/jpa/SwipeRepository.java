package com.attendance.swipe.SwipeService.jpa;

import com.attendance.swipe.SwipeService.Employee.Employee;
import com.attendance.swipe.SwipeService.Employee.SwipeRecord;
import com.attendance.swipe.SwipeService.Employee.SwipeRecordKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SwipeRepository  extends CrudRepository<SwipeRecord, SwipeRecordKey> {
}
