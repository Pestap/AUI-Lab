package eti.pg.lab.initializer;

import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final PilotService pilotService;


    @Autowired
    public Initializer(PilotService pilotService){
        this.pilotService = pilotService;
    }

    @PostConstruct
    private synchronized void init(){


        Pilot p1 = Pilot.builder()
                .name("Piotr")
                .surname("Pesta")
                .id(1)
                .dateOfBirth(LocalDate.of(2001,3,12))
                .build();

        Pilot p2 = Pilot.builder()
                .name("Jan")
                .surname("Kowalski")
                .id(2)
                .dateOfBirth(LocalDate.of(1969, 7, 19))
                .build();

        pilotService.create(p1);
        pilotService.create(p2);

    }

}
