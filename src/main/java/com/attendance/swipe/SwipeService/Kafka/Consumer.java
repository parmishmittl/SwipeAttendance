package com.attendance.swipe.SwipeService.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer {

    @KafkaListener(topics = "hobbit", groupId = "kafkaapp-consumer")
    public void getMessage(String message) {
        System.out.println("This is the message : " + message);
    }

}
