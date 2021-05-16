package ES_lab.followSky.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ES_lab.followSky.api.ExternalApi;
import ES_lab.followSky.kafka.Consumer;
import ES_lab.followSky.models.Flight;
import ES_lab.followSky.repository.FlightRepository;

@Component
@Service
@Transactional
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LogManager.getLogger(FlightServiceImpl.class);

    private Map<String, Integer> historicFlightsPerCountry = new HashMap<String, Integer>();

    private Map<String, Float> historicGeneralStatistics = new HashMap<String, Float>();

    private List<String> savedIcao24 = new ArrayList<String>();

    private List<String> flightsDisappeared = new ArrayList<String>();

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ExternalApi restUtil;

    @Autowired
    private Consumer kafkaConsumer;

    public FlightServiceImpl() {
        historicGeneralStatistics.put("total_flights", (float) 0); // total number of flights registered
        historicGeneralStatistics.put("on_ground", (float) 0); // total number of aircrafts on ground registered
        historicGeneralStatistics.put("max_velocity", (float) 0); // maximum velocity registered
        historicGeneralStatistics.put("max_altitude", (float) 0); // maximum baro altitude registered
        historicGeneralStatistics.put("max_longitude", (float) 0); // maximum longitude registered
        historicGeneralStatistics.put("max_latitude", (float) 0); // maximum latitude registered
    }

    @Override
    public Flight getFlightByIcao24(String icao24) {
        return flightRepository.findByIcao24(icao24);
    }

    @Override
    public List<Flight> getAllFlightsNow() {
        List<Flight> flights= flightRepository.findAll();
        List<Flight> flightsNow = new ArrayList<Flight>();
        long currentTime = Instant.now().getEpochSecond();
        for (Flight f: flights) {
            if (currentTime - f.getTime() <= 15) flightsNow.add(f);   // only flights created/updated on the last 15 seconds -> online flights
        }
        return flightsNow;
    }

    @Override
    public Map<String, Integer> getHistoricPerCountry() {
        return historicFlightsPerCountry;
    }

    @Override
    public Map<String, Float> getHistoricStatistics() {
        return historicGeneralStatistics;
    }


    @Override
    public List<String> getFlightsDisappeared(){
        List<Flight> flightsNow = getAllFlightsNow();
        List<String> flightsToMonitor = kafkaConsumer.getFlightsToMonitor();

        if (!flightsToMonitor.isEmpty() && !flightsNow.isEmpty()) {
            for(String icao24: flightsToMonitor) {
                boolean disappeared = true;
                for(Flight f: flightsNow) {
                    if (f.getIcao24().equals(icao24)) disappeared = false;
                }
                if (disappeared == true && !flightsDisappeared.contains(icao24)) flightsDisappeared.add(icao24);
            }
        }
        return flightsDisappeared;
    }

    @Override
    public void deleteAll() {
        flightRepository.deleteAll();
    }

    @Override
    public void deleteFlightByIcao24(String icao24) {
        flightRepository.deleteByIcao24(icao24);
    }

    @Override
    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }

    // Pushing from external API (OpenSky) every 10 seconds
    @Scheduled(fixedRate = 10000)
    @Override
    public void saveFlightsNow() {

        try {
            logger.info("Pushing from OpenSky API to repository...");

            List<Flight> flights = restUtil.getFlightsInfoNow();

            for (Flight flight: flights) {

                if ( flightRepository.findByIcao24(flight.getIcao24()) != null) {
                    flightRepository.deleteByIcao24(flight.getIcao24());
                }

                flightRepository.saveAndFlush(flight);
              
                //if (flight.getVelocity() == 0) logger.info("Flight " + flight.getIcao24() + " is not moving");
            }

            logger.info("Repository updated with new data!");
            logger.warn("Next updating in 10 seconds!");
        }
        catch (Exception e) {
            logger.error("ERROR! Error updating repository with data from OpenSky API! -> " + e);
            logger.warn("No updates into repository were made!");
            logger.warn("Next update try in 10 seconds!");
        }

    }


    // Pushing new data from database to HashMap every 2 hours
    @Scheduled(fixedRate = 2*3600000) 
    @Override
    public void saveHistoric() {

        try {
            logger.info("Updating HashMap with new info from database...");

            List<Flight> flights = flightRepository.findAll();  // returns all flights accumulated -> all stored flights
            
            for (Flight f : flights) {

                if (f.getOrigin_country() != null && f.getOrigin_country() != "") {

                    if (historicFlightsPerCountry.containsKey(f.getOrigin_country())) {
                        historicFlightsPerCountry.put(f.getOrigin_country(), historicFlightsPerCountry.get(f.getOrigin_country()) +1);

                    }
                    else {
                        historicFlightsPerCountry.put(f.getOrigin_country(), 1);
                    }
                }

                // general statistics
                
                if (!savedIcao24.contains(f.getIcao24())) {
                    historicGeneralStatistics.put("total_flights", historicGeneralStatistics.get("total_flights")+1);
                    if (f.getOn_ground() == "true") historicGeneralStatistics.put("on_ground", historicGeneralStatistics.get("on_ground")+1);
                    savedIcao24.add(f.getIcao24());
                }
                if (f.getVelocity() > historicGeneralStatistics.get("max_velocity")) historicGeneralStatistics.put("max_velocity", f.getVelocity());
                if (f.getBaro_altitude() > historicGeneralStatistics.get("max_altitude")) historicGeneralStatistics.put("max_altitude", f.getBaro_altitude());
                if (f.getLatitude() > historicGeneralStatistics.get("max_latitude")) historicGeneralStatistics.put("max_latitude", f.getLatitude());
                if (f.getLongitude() > historicGeneralStatistics.get("max_longitude")) historicGeneralStatistics.put("max_longitude", f.getLongitude());

            }
            
            logger.info("HashMaps updated with new data!");
            logger.warn("Next update in 2 hours!");
        }

        catch (Exception e) {
            logger.error("ERROR! Error updating HashMaps with data from OpenSky API! -> " + e);
            logger.warn("No updates into HashMaps were made!");
            logger.warn("Next update try in 2 hours!");
        }
    }

}