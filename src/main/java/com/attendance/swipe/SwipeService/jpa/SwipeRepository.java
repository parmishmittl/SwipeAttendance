package com.attendance.swipe.SwipeService.jpa;

import com.attendance.swipe.SwipeService.Employee.Employee;
import com.attendance.swipe.SwipeService.Employee.SwipeRecord;
import com.attendance.swipe.SwipeService.Employee.SwipeRecordKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SwipeRepository  extends CrudRepository<SwipeRecord, SwipeRecordKey> {

       // Custom method to perform an "upsert"
       //
//       @Query(nativeQuery = "INSERT INTO swipe_record (id,date,emp_id,swipe_in,swipe_out) VALUES (:id,:date,:empId,:swipeIn,:swipeOut) ON DUPLICATE KEY UPDATE swipe_out=:swipeOut")
//    SwipeRecord saveOrUpdate(SwipeRecord entity);

}
