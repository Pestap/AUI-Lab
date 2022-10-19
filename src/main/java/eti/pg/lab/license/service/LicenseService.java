package eti.pg.lab.license.service;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

    private LicenseRepository repository;

    public LicenseService(LicenseRepository repository){
        this.repository= repository;
    }

    public Optional<License> find(int id){
        return repository.find(id);
    }

    public List<License> findAll(){
        return repository.findAll();
    }

    public void create(License license){
        repository.create(license);
    }

    public void delete(int id){
        repository.delete(id);
    }
}
