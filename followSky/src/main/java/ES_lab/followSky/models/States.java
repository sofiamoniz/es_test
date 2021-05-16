package ES_lab.followSky.models;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class States{
    
    private int time;
    private List<List> states;

    @JsonIgnore
    private List<Flight> parsed_flights;

    public States(){
    }

    public List<Flight> getFlights() {
        return parsed_flights;
    }

    public void setStates(List<List> states) {
        parsed_flights = new LinkedList<>();
        for (List state : states ) {
            parsed_flights.add(new Flight(state));
        }
    }

}
