package eti.pg.lab.plane.repository;

import eti.pg.lab.datastorage.DataStorage;
import eti.pg.lab.plane.entity.Plane;
import eti.pg.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class PlaneRepository implements Repository<Plane, String> {
    private DataStorage dataStorage;

    @Autowired
    public PlaneRepository(DataStorage storage){
        this.dataStorage = storage;
    }

    @Override
    public Optional<Plane> find(String typeName){
        return dataStorage.findPlane(typeName);
    }

    @Override
    public List<Plane> findAll(){
        return dataStorage.findAllPlanes();
    }

    @Override
    public void create(Plane plane){
        dataStorage.addPlane(plane);
    }

    @Override
    public void delete(String typeName){
        dataStorage.deletePlane(typeName);
    }

}
