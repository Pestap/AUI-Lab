package eti.pg.lab.license.service;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.repository.LicenseRepository;
import eti.pg.lab.pilot.entity.Pilot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

    private LicenseRepository repository;

    @Autowired
    public LicenseService(LicenseRepository repository){
        this.repository= repository;
    }

    public Optional<License> find(int id){
        return repository.findById(id);
    }
    public Optional<License> find(Pilot pilot, int id){
        return repository.findByPilotAndId(pilot, id);
    }
    public List<License> findAll(){
        return repository.findAll();
    }

    public List<License> findAll(Pilot pilot){
        return repository.findAllByPilot(pilot);
    }

    @Transactional
    public License create(License license){
        return repository.save(license);
    }
    @Transactional
    public void update(License license){
        repository.save(license);
    }
    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }
}
