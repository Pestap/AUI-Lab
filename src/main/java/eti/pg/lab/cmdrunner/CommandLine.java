package eti.pg.lab.cmdrunner;

import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.license.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLine implements org.springframework.boot.CommandLineRunner {
    private PilotService pilotService;
    private LicenseService licenseService;

    @Autowired
    public CommandLine(PilotService pilotService, LicenseService licenseService){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
    }

    @Override
    public void run(String... args) throws Exception{
        /**
         * here the client communication takes place
         */

        CmdInterface UI = new CmdInterface(new Scanner(System.in), pilotService, licenseService);
        UI.DisplayUI();

    }
}
