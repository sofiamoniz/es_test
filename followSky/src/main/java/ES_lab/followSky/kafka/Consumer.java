package ES_lab.followSky.kafka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger logger = LogManager.getLogger(Consumer.class);

    List<String> flightsToMonitor = new ArrayList<String>();

    @KafkaListener(topics = "flights", groupId = "eslab")
    public void consume(String message) throws IOException {
        logger.info(String.format("KAFKA Consumed message -> %s", message));
        flightsToMonitor.add(message);
    }

    public List<String> getFlightsToMonitor() {
        return flightsToMonitor;
    }

}
