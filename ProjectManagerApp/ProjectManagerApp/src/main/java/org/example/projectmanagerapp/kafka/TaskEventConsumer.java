package org.example.projectmanagerapp.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {

    @KafkaListener(topics = "task-events", groupId = "task-group")
    public void listen(String message){
        System.out.println("Received: " + message);
    }

}
