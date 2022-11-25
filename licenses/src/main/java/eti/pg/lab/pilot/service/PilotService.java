package eti.pg.lab.pilot.service;

import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.repository.PilotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PilotService {

    private PilotRepository repository;

    @Autowired
    public PilotService(PilotRepository repository){
        this.repository = repository;
    }

    public Optional<Pilot> find(int id){
        return repository.findById(id);
    }
    public List<Pilot> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Pilot create(Pilot pilot){
        return repository.save(pilot);
    }
    @Transactional
    public void update(Pilot pilot){
        repository.save(pilot);
    }
    @Transactional
    public void delete(int id){
        repository.deleteById(id);
    }
}
