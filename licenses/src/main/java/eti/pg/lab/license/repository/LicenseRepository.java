package eti.pg.lab.license.repository;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.pilot.entity.Pilot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    Optional<License> findById(int id);
    Optional<License> findByPilotAndId(Pilot pilot, int id);
    List<License> findAll();
    List<License> findAllByPilot(Pilot pilot);
}
