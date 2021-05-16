package ES_lab.followSky.models;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Entity
@ToString
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String icao24;
    private String callsign;
    private String origin_country;
    private int time_position;
    private int last_contact;
    private float longitude;
    private float latitude;
    private float baro_altitude;
    private String on_ground;
    private float velocity;
    private float true_track;
    private float vertical_rate;
    private String sensors;
    private float geo_altitude;
    private String squawk;
    private String spi;
    private int position_source;
    private long time;

    public Flight() {
    }

    public Flight(List state) {
        setIcao24(state.get(0));
        setCallsign(state.get(1));
        setOrigin_country(state.get(2));
        setTime_position(state.get(3));
        setLast_contact(state.get(4));
        setLongitude(state.get(5));
        setLatitude(state.get(6));
        setBaro_altitude(state.get(7));
        setOn_ground(state.get(8));
        setVelocity(state.get(9));
        setTrue_track(state.get(10));
        setVertical_rate(state.get(11));
        setSensors(state.get(12));
        setGeo_altitude(state.get(13));
        setSquawk(state.get(14));
        setSpi(state.get(15));
        setPosition_source(state.get(16));
        setTime(Instant.now().getEpochSecond());
    }

    public void setIcao24(Object icao24) {
        if (icao24 == null) {
            this.icao24 = "";
        }
        else {
            this.icao24 = icao24.toString();
        }
    }

    public void setCallsign(Object callsign) {
        if (callsign == null) {
            this.callsign = "";
        }
        else {
            this.callsign = callsign.toString();
        }
    }

    public void setOrigin_country(Object origin_country) {
        if (origin_country == null) {
            this.origin_country = "";
        }
        else {
            this.origin_country = origin_country.toString();
        }
    }

    public void setTime_position(Object time_position) {
        try {
            this.time_position = Integer.parseInt(time_position.toString());
        }
        catch (Exception e) {
            this.time_position = 0;
        }
    }

    public void setLast_contact(Object last_contact) {
        try {
            this.last_contact = Integer.parseInt(last_contact.toString());
        }
        catch (Exception e) {
            this.last_contact = 0;
        }
    }

    public void setLongitude(Object longitude) {
        try {
            this.longitude = Float.parseFloat(longitude.toString());
        }
        catch (Exception e) {
            this.longitude = 0;
        }
    }

    public void setLatitude(Object latitude) {
        try {
            this.latitude = Float.parseFloat(latitude.toString());
        }
        catch (Exception e) {
            this.latitude = 0;
        }
    }

    public void setBaro_altitude(Object baro_altitude) {
        try {
            this.baro_altitude = Float.parseFloat(baro_altitude.toString());
        }
        catch (Exception e) {
            this.baro_altitude = 0;
        }
    }

    public void setOn_ground(Object on_ground) {
        if (on_ground == null) {
            this.on_ground = "";
        }
        else {
            this.on_ground = on_ground.toString();
        }
    }

    public void setVelocity(Object velocity) {
        try {
            this.velocity = Float.parseFloat(velocity.toString());
        }
        catch (Exception e) {
            this.velocity = 0;
        }
    }

    public void setTrue_track(Object true_track) {
        try {
            this.true_track = Float.parseFloat(true_track.toString());
        }
        catch (Exception e) {
            this.true_track = 0;
        }
    }

    public void setVertical_rate(Object vertical_rate) {
        try {
            this.vertical_rate = Float.parseFloat(vertical_rate.toString());
        }
        catch (Exception e) {
            this.vertical_rate = 0;
        }
    }

    public void setSensors(Object sensors) {
        if (sensors == null) {
            this.sensors = "";
        }
        else {
            this.sensors = sensors.toString();
        }
    }

    public void setGeo_altitude(Object geo_altitude) {
        try {
            this.geo_altitude = Float.parseFloat(geo_altitude.toString());
        }
        catch (Exception e) {
            this.geo_altitude = 0;
        }
    }

    public void setSquawk(Object squawk) {
        if (squawk == null) {
            this.squawk = "";
        }
        else {
            this.squawk = squawk.toString();
        }
    }

    public void setSpi(Object spi) {
        if (spi == null) {
            this.spi = "";
        }
        else {
            this.spi = spi.toString();
        }
    }

    public void setPosition_source(Object position_source) {
        try {
            this.position_source = Integer.parseInt(position_source.toString());
        }
        catch (Exception e) {
            this.position_source = 0;
        }
    }

    public void setTime(long time) {
        this.time = time;
    }

}


