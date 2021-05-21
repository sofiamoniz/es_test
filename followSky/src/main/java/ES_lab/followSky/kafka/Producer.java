package ES_lab.followSky.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
public class Producer {

    private static final Logger logger = LogManager.getLogger(Producer.class);
    private static final String TOPIC = "flights";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("KAFKA Produced message -> %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}
