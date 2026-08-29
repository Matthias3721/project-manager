package org.example.projectmanagerapp.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTaskCreatedEvent(String message){
        kafkaTemplate.send("task-events", message);
    }

}
