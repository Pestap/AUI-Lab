package eti.pg.lab.pilot.controller;

import eti.pg.lab.license.service.LicenseService;
import eti.pg.lab.pilot.dto.CreatePilotRequest;
import eti.pg.lab.pilot.dto.GetPilotResponse;
import eti.pg.lab.pilot.dto.GetPilotsResponse;
import eti.pg.lab.pilot.dto.UpdatePilotRequest;
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

    @GetMapping
    public ResponseEntity<GetPilotsResponse> getPilots(){
        return ResponseEntity.ok(GetPilotsResponse.entityToDtoMapper().apply(pilotService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetPilotResponse> getPilot(@PathVariable("id") int id){
        return pilotService.find(id)
                .map(value -> ResponseEntity
                        .ok(GetPilotResponse.entityToDtoMapper().apply(value)))
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    /*@PostMapping
    public ResponseEntity<Void> createPilot(@RequestBody CreatePilotRequest request, UriComponentsBuilder builder){
        Pilot pilot  = CreatePilotRequest
                .dtoToEntityMapper().apply(request);

        pilot = pilotService.create(pilot);

        return ResponseEntity
                .created(builder
                        .pathSegment("api", "pilots", "{id}")
                        .buildAndExpand(pilot.getId()).toUri())
                .build();
    }*/

    @PutMapping
    public ResponseEntity<Void> createPilot(@RequestBody CreatePilotRequest request, UriComponentsBuilder builder){
        Pilot pilot  = CreatePilotRequest
                .dtoToEntityMapper().apply(request);

        pilot = pilotService.create(pilot);

        return ResponseEntity
                .created(builder
                        .pathSegment("api", "pilots", "{id}")
                        .buildAndExpand(pilot.getId()).toUri())
                .build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updatePilot(@RequestBody UpdatePilotRequest request, @PathVariable("id") int id){
        Optional<Pilot> pilotToUpdate = pilotService.find(id);
        if(pilotToUpdate.isPresent()){
            UpdatePilotRequest.dtoToEntityUpdater().apply(pilotToUpdate.get(), request);
            pilotService.update(pilotToUpdate.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
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
