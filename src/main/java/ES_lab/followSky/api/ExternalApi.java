package ES_lab.followSky.api;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ES_lab.followSky.models.Flight;
import ES_lab.followSky.models.States;


@Component
public class ExternalApi{

    private RestTemplate template = new RestTemplate();

    private static final Logger logger = LogManager.getLogger(ExternalApi.class);


    public List<Flight> getFlightsInfoNow() {

        // current time
        
        String uRL = "https://opensky-network.org/api/states/all?lamin=45&lomin=9&lamax=48&lomax=10"; // somewhere in Switzerland...

        States states = template.getForObject(uRL, States.class);
        
        logger.info("Success obtaining current data from External Api (OpenSky) and JSON mapping.");

        return states.getFlights();
        
    }

}