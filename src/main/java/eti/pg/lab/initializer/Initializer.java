package eti.pg.lab.initializer;

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


    @Autowired
    public Initializer(PilotService pilotService, LicenseService licenseService){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
    }

    @PostConstruct
    private synchronized void init(){

        /*License a320 = License.builder()
                .licenseId("Airbus A320")
                .capacity(180)
                .issueDate(LocalDate.of(1987,2,22))
                .description("One of the most popular narrow-body airliners int the world. ")
                .build();

        License a380 = License.builder()
                .licenseId("Airbus A380")
                .capacity(560)
                .issueDate(LocalDate.of(2005,4,27))
                .description("The biggest airliner in the world. ")
                .build();
        */

        License lic1 = License.builder()
                .licenseId(1)
                .privilegeLevel("sport")
                .issueDate(LocalDate.of(2005, 4, 21))
                .description("A sport license")
                .build();

        License lic2 = License.builder()
                        .licenseId(2)
                        .privilegeLevel("private")
                        .issueDate(LocalDate.of(2008, 5,12))
                        .description("May fly for pleasure or personal business. Private pilots cannot be paid, compensated to fly, or hired by any operator.")
                        .build();
        licenseService.create(lic1);
        licenseService.create(lic2);

        List<License> licenseList = new ArrayList<>();
        licenseList.add(lic1);
        licenseList.add(lic2);

        Pilot p1 = Pilot.builder()
                .name("Piotr")
                .surname("Pesta")
                .id(1)
                .dateOfBirth(LocalDate.of(2001,3,12))
                .licenseList(licenseList)
                .build();

        Pilot p2 = Pilot.builder()
                .name("Jan")
                .surname("Kowalski")
                .id(2)
                .dateOfBirth(LocalDate.of(1969, 7, 19))
                .licenseList(new ArrayList<>())
                .build();

        pilotService.create(p1);
        pilotService.create(p2);


    }

}
