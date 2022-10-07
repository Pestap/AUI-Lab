package eti.pg.lab.initializer;

import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.plane.entity.Plane;
import eti.pg.lab.plane.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final PilotService pilotService;
    private final PlaneService planeService;


    @Autowired
    public Initializer(PilotService pilotService, PlaneService planeService){
        this.pilotService = pilotService;
        this.planeService = planeService;
    }

    @PostConstruct
    private synchronized void init(){

        Plane a320 = Plane.builder()
                .typeName("Airbus A320")
                .capacity(180)
                .launchDate(LocalDate.of(1987,2,22))
                .description("One of the most popular narrow-body airliners int the world. ")
                .build();

        Plane a380 = Plane.builder()
                .typeName("Airbus A380")
                .capacity(560)
                .launchDate(LocalDate.of(2005,4,27))
                .description("The biggest airliner in the world. ")
                .build();

        planeService.create(a320);
        planeService.create(a380);

        List<Plane> planeList = new ArrayList<>();
        planeList.add(a380);
        planeList.add(a320);

        Pilot p1 = Pilot.builder()
                .name("Piotr")
                .surname("Pesta")
                .id(1)
                .dateOfBirth(LocalDate.of(2001,3,12))
                .planeCertificationList(planeList)
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
