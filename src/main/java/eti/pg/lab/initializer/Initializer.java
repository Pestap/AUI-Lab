package eti.pg.lab.initializer;

import eti.pg.lab.file.service.FileDBService;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer {

    private final PilotService pilotService;
    private final LicenseService licenseService;

    private final FileDBService fileDBService;


    @Autowired
    public Initializer(PilotService pilotService, LicenseService licenseService, FileDBService fileservice){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
        this.fileDBService = fileservice;
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


        License lic1 = License.builder()
                .id(1)
                .privilegeLevel("sport")
                .issueDate(LocalDate.of(2005, 4, 21))
                .description("A sport license")
                .pilot(p1)
                .build();

        License lic2 = License.builder()
                .id(2)
                .privilegeLevel("private")
                .issueDate(LocalDate.of(2008, 5,12))
                .description("May fly for pleasure or personal business. Private pilots cannot be paid, compensated to fly, or hired by any operator.")
                .pilot(p1)
                .build();

        licenseService.create(lic1);
        licenseService.create(lic2);

    }

}
