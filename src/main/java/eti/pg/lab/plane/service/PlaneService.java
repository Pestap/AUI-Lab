package eti.pg.lab.plane.service;

import eti.pg.lab.plane.entity.Plane;
import eti.pg.lab.plane.repository.PlaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaneService {

    private PlaneRepository repository;

    public PlaneService(PlaneRepository repository){
        this.repository= repository;
    }

    public Optional<Plane> find(String typeName){
        return repository.find(typeName);
    }

    public List<Plane> findAll(){
        return repository.findAll();
    }

    public void create(Plane plane){
        repository.create(plane);
    }

    public void delete(String typeName){
        repository.delete(typeName);
    }
}
