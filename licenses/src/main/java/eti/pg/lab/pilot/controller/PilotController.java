package eti.pg.lab.pilot.controller;

import eti.pg.lab.license.service.LicenseService;
import eti.pg.lab.pilot.dto.CreatePilotRequest;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/pilots")
public class PilotController {
    private PilotService pilotService;
    private LicenseService licenseService;

    @Autowired
    public PilotController(PilotService pilotService, LicenseService licenseService){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
    }

    @PostMapping
    public ResponseEntity<Void> createPilotByPost(@RequestBody CreatePilotRequest request, UriComponentsBuilder builder){
        Pilot pilot  = CreatePilotRequest
                .dtoToEntityMapper().apply(request);

        pilot = pilotService.create(pilot);

        return ResponseEntity
                .created(builder
                        .pathSegment("api", "pilots", "{id}")
                        .buildAndExpand(pilot.getId()).toUri())
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePilot(@PathVariable("id") int id){
        Optional<Pilot> pilotToDelete = pilotService.find(id);
        if(pilotToDelete.isPresent()){
            pilotService.delete(id);
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
