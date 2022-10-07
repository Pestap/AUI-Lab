package eti.pg.lab.cmdrunner;

import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.plane.service.PlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLine implements org.springframework.boot.CommandLineRunner {
    private PilotService pilotService;
    private PlaneService planeService;

    @Autowired
    public CommandLine(PilotService pilotService, PlaneService planeService){
        this.pilotService = pilotService;
        this.planeService = planeService;
    }

    @Override
    public void run(String... args) throws Exception{
        /**
         * here the client communication takes place
         */

        CmdInterface UI = new CmdInterface(new Scanner(System.in), pilotService, planeService);
        UI.DisplayUI();

    }
}
