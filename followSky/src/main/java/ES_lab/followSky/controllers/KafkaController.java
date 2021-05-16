package ES_lab.followSky.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import ES_lab.followSky.kafka.Producer;

@CrossOrigin 
@RestController
public class KafkaController {

    private final Producer producer;

    @Autowired
    KafkaController(Producer producer) {
        this.producer = producer;
    }

    @CrossOrigin(origins="*")
    @GetMapping(value = "/kafkaPublish")
    public void sendMessageToKafkaTopic(@RequestParam("flight") String message) {
        this.producer.sendMessage(message);
    }
}