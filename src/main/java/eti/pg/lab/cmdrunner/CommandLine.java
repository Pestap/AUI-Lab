package eti.pg.lab.cmdrunner;

import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLine implements org.springframework.boot.CommandLineRunner {
    private PilotService pilotService;

    @Autowired
    public CommandLine(PilotService pilotService){
        this.pilotService = pilotService;
    }

    @Override
    public void run(String... args) throws Exception{
        /**
         * here the client communication takes place
         */

        pilotService.findAll().forEach(System.out::println);





    }
}
