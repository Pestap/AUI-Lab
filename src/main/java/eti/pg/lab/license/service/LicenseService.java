package eti.pg.lab.license.service;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.repository.LicenseRepository;
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

    public List<License> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void create(License license){
        repository.save(license);
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
