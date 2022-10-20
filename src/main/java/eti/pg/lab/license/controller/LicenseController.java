package eti.pg.lab.license.controller;

import eti.pg.lab.license.dto.CreateLicenseRequest;
import eti.pg.lab.license.dto.GetLicenseResponse;
import eti.pg.lab.license.dto.GetLicensesResponse;
import eti.pg.lab.license.dto.UpdateLicenseRequest;
import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.service.LicenseService;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/licenses")
public class LicenseController {
    private PilotService pilotService;
    private LicenseService licenseService;

    @Autowired
    public LicenseController(PilotService pilotService, LicenseService licenseService){
        this.pilotService = pilotService;
        this.licenseService = licenseService;
    }
    @GetMapping
    public ResponseEntity<GetLicensesResponse> getLicenses(){
        return ResponseEntity.ok(GetLicensesResponse.entityToDtoMapper().apply(licenseService.findAll()));
    }
    @GetMapping("{id}")
    public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("id") int id){
        return licenseService.find(id)
                .map(value -> ResponseEntity
                        .ok(GetLicenseResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Void> createLicense(@RequestBody CreateLicenseRequest request, UriComponentsBuilder builder){
        License licenseToAdd = CreateLicenseRequest.dtoToEntityMapper(id -> pilotService.find(id).orElseThrow()).apply(request);
        licenseToAdd = licenseService.create(licenseToAdd);

        return ResponseEntity.created(builder
                .pathSegment("api", "licenses", "{id}")
                .buildAndExpand(licenseToAdd.getId()).toUri())
            .build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateLicense(@RequestBody UpdateLicenseRequest request, @PathVariable("id") int id){
        Optional<License> licenseToUpdate = licenseService.find(id);

        if(licenseToUpdate.isPresent()){
            UpdateLicenseRequest.dtoToEntityUpdater().apply(licenseToUpdate.get(), request);
            licenseService.update(licenseToUpdate.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable("id") int id){
        Optional<License> licenseToDelete = licenseService.find(id);
        if(licenseToDelete.isPresent()){
            licenseService.delete(id);
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
