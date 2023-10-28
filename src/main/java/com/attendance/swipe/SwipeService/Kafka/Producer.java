package com.attendance.swipe.SwipeService.Kafka;

import com.attendance.swipe.SwipeService.Employee.AttendanceEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    public static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);
    private final KafkaTemplate<Long, AttendanceEvent> template;
    private ObjectMapper objectMapper;

    @Autowired
    public Producer(KafkaTemplate<Long, AttendanceEvent> template, ObjectMapper objectMapper) {
        this.template = template;
        this.objectMapper = objectMapper;
    }

    public Producer(KafkaTemplate<Long, AttendanceEvent> template) {
        this.template = template;
    }


    public void sendMessage(AttendanceEvent attendanceEvent) {
        try {

            template.send("AttendanceData", Long.parseLong(String.valueOf(attendanceEvent.getEmpId())), attendanceEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
