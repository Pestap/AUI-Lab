package eti.pg.lab.license.service;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.repository.LicenseRepository;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.repository.PilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

    private LicenseRepository licenseRepository;
    private PilotRepository pilotRepository;

    @Autowired
    public LicenseService(LicenseRepository licenseRepository, PilotRepository pilotRepository){
        this.licenseRepository = licenseRepository;
        this.pilotRepository = pilotRepository;
    }

    public Optional<License> find(int id){
        return licenseRepository.findById(id);
    }

    /**
     * find license with pilot id and license id
     * @param pilotId - pilotID
     * @param id - license id
     * @return - List of licenses
     */
    public Optional<License> find(int pilotId, int id){
        Optional<Pilot> pilot = pilotRepository.findById(pilotId);
        if(pilot.isPresent()){
            return licenseRepository.findByPilotAndId(pilot.get(), id);
        }else{
            return Optional.empty();
        }

    }
    public List<License> findAll(){
        return licenseRepository.findAll();
    }

    public List<License> findAll(Pilot pilot){
        return licenseRepository.findAllByPilot(pilot);
    }

    @Transactional
    public License create(License license){
        return licenseRepository.save(license);
    }
    @Transactional
    public void update(License license){
        licenseRepository.save(license);
    }
    @Transactional
    public void delete(int id){
        licenseRepository.deleteById(id);
    }

}
