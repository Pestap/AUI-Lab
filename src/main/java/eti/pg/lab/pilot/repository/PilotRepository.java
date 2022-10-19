package eti.pg.lab.pilot.repository;

import eti.pg.lab.pilot.entity.Pilot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer> {

    Optional<Pilot> findAllById(int id);
    List<Pilot> findAll();

}
