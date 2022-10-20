package eti.pg.lab.license.controller;


import eti.pg.lab.license.dto.CreateLicenseRequest;
import eti.pg.lab.license.dto.GetLicenseResponse;
import eti.pg.lab.license.dto.GetLicensesResponse;
import eti.pg.lab.license.dto.UpdateLicenseRequest;
import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.service.LicenseService;
import eti.pg.lab.pilot.controller.PilotController;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/pilots/{pilotId}/licenses")
public class PilotLicenseController
{
    private PilotService pilotService;
    private LicenseService licenseService;

    @Autowired
    public PilotLicenseController(PilotService pilotService, LicenseService licenseService){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
    }

    @GetMapping
    public ResponseEntity<GetLicensesResponse> getLicenses(@PathVariable("pilotId") int pilotId){
        Optional<Pilot> pilot = pilotService.find(pilotId);
        return pilot.map(value -> ResponseEntity
                .ok(GetLicensesResponse.entityToDtoMapper()
                        .apply(licenseService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{licenseId}")
    public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("pilotId") int pilotId,
                                                         @PathVariable("licenseId") int licenseId){
        return licenseService.find(pilotId, licenseId)
                .map(value -> ResponseEntity.ok(GetLicenseResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping
    public ResponseEntity<Void> createLicense(@PathVariable("pilotId") int pilotId,
                                              @RequestBody CreateLicenseRequest request,
                                              UriComponentsBuilder builder){
        request.setPilotId(pilotId);
        Optional<Pilot> pilot = pilotService.find(pilotId);
        if(pilot.isPresent()){
            License licenseToAdd = CreateLicenseRequest
                    .dtoToEntityMapper(id -> pilotService.find(id).orElseThrow())
                    .apply(request);
            licenseToAdd = licenseService.create(licenseToAdd);
            return ResponseEntity.created(builder.pathSegment("api", "pilots", "{pilotId}", "licenses", "{licenseId}")
                    .buildAndExpand(pilot.get().getId(), licenseToAdd.getId()).toUri()).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("{licenseId}")
    public ResponseEntity<Void> updateLicense(@PathVariable ("pilotId") int pilotId,
                                              @RequestBody UpdateLicenseRequest request,
                                              @PathVariable("licenseId") int licenseId){
        Optional<License> licenseToUpdate = licenseService.find(pilotId, licenseId);
        if(licenseToUpdate.isPresent()){
            UpdateLicenseRequest.dtoToEntityUpdater().apply(licenseToUpdate.get(), request);
            licenseService.update(licenseToUpdate.get());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{licenseId}")
    public ResponseEntity<Void> deleteLicense(@PathVariable("pilotId") int pilotId,
                                              @PathVariable("licenseId") int licenseId){
        Optional<Pilot> pilot = pilotService.find(pilotId);
        if(pilot.isPresent()){
            Optional<License> license = licenseService.find(pilotId, licenseId);
            if(license.isPresent()){
                licenseService.delete(license.get().getId());
                return ResponseEntity.accepted().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
