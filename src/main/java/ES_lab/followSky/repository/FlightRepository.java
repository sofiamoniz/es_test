package ES_lab.followSky.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ES_lab.followSky.models.Flight;

@Repository
@Transactional
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAll();

    Flight findByIcao24(String icao24);

    void deleteAll();

    void deleteByIcao24(String icao24);
    
}


