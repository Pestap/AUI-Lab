package eti.pg.lab.license.repository;

import eti.pg.lab.license.entity.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    Optional<License> findAllByLicenseId(int licenseId);
    List<License> findAll();

}
