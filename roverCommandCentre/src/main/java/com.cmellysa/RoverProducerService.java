package com.cmellysa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoverProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC = "mars-rover";

    @Autowired
    public RoverProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRoverCommands(List<RoverData> roverData) {
        for (RoverData rover : roverData) {
            String key = rover.getRoverId();
            String message = rover.getPosition() + ":" + rover.getCommands();
            kafkaTemplate.send(TOPIC, key, message);
        }
    }
}

