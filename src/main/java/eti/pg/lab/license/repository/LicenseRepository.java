package eti.pg.lab.license.repository;

import eti.pg.lab.datastorage.DataStorage;
import eti.pg.lab.license.entity.License;
import eti.pg.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class LicenseRepository implements Repository<License, Integer> {
    private DataStorage dataStorage;

    @Autowired
    public LicenseRepository(DataStorage storage){
        this.dataStorage = storage;
    }

    @Override
    public Optional<License> find(Integer id){
        return dataStorage.findLicense(id);
    }

    @Override
    public List<License> findAll(){
        return dataStorage.findAllLicenses();
    }

    @Override
    public void create(License license){
        dataStorage.addLicense(license);
    }

    @Override
    public void delete(Integer id){
        dataStorage.deleteLicense(id);
    }

}
