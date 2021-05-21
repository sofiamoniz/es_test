package ES_lab.followSky.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ES_lab.followSky.models.Flight;
import ES_lab.followSky.service.FlightServiceImpl;

@CrossOrigin 
@RestController
public class FlightController {

    @Autowired
    private FlightServiceImpl flightService;

	// http://localhost:8080/flight
    @CrossOrigin(origins="*")
	@GetMapping(value="/flight")
    public List<Flight> all() {
        return flightService.getAllFlightsNow();
	}

	// http://localhost:8080/historicPerCountry
	@GetMapping(value="/historicPerCountry")
	public Map<String, Integer> historicPerCountry(){
		return flightService.getHistoricPerCountry();
	}

	// http://localhost:8080/historicStatistics
	@GetMapping(value="/historicStatistics")
	public Map<String, Float> historicStatistics(){
		return flightService.getHistoricStatistics();
	}

	// http://localhost:8080/flightsDisappeared 
	@GetMapping(value="/flightsDisappeared")
	public List<String> flightsDisappeared(){
		return flightService.getFlightsDisappeared();
	}


	
/** 
	// http://localhost:8080/departures?airport=EDDF&begin=1517227200&end=1517230800
	@GetMapping("/departures")
	public String departures(@RequestParam(value = "airport") String airport, @RequestParam(value = "begin") String begin, @RequestParam(value = "end") String end) throws OpenSkyException{
		ExternalApi api = new ExternalApi();
		return api.getInfoForDeparture(airport, begin, end);
	}

	// http://localhost:8080/states
	// http://localhost:8080/states?lamin=24
	@GetMapping("/states")
	public String states(@RequestParam(name="lamin", required=false, defaultValue = "45.8389") double lamin,
						 @RequestParam(name="lomin", required=false, defaultValue = "5.9962") double lomin,
						 @RequestParam(name="lamax", required=false, defaultValue = "47.8229") double lamax,
						 @RequestParam(name="lomax", required=false, defaultValue = "10.5226") double lomax,
						 Model model) throws OpenSkyException{
		ExternalApi api = new ExternalApi();
		return api.getInfoForState(lamin, lomin, lamax, lomax);
	}
**/
}

