package eti.pg.lab.pilot.repository;

import eti.pg.lab.datastorage.DataStorage;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class PilotRepository implements Repository<Pilot, Integer> {

    private DataStorage dataStorage;

    /**
     * @param storage - data storage (for now in memory)
     */
    @Autowired
    public PilotRepository(DataStorage storage){
        this.dataStorage = storage;
    }

    @Override
    public Optional<Pilot> find(Integer id){
        return dataStorage.findPilot(id);
    }

    @Override
    public List<Pilot> findAll(){
        return dataStorage.findAllPilots();
    }

    @Override
    public void create(Pilot pilot){
        dataStorage.addPilot(pilot);
    }

    @Override
    public void delete(Integer id){
        dataStorage.deletePilot(id);
    }

}
