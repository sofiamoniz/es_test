package ES_lab.followSky.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ES_lab.followSky.models.Flight;


@Service
public interface FlightService {

    Flight getFlightByIcao24(String icao24);

    void deleteFlightByIcao24(String ica024);

    void deleteAll();

    List<Flight> getAllFlightsNow();

    Map<String, Integer> getHistoricPerCountry();

    Map<String, Float> getHistoricStatistics();

    List<String> getFlightsDisappeared();

    void saveFlight(Flight flight);

    void saveFlightsNow();

    void saveHistoric();

}

