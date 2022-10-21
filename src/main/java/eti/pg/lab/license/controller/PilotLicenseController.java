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

import java.util.List;
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

    /**
     * Get all licenses belonging to specified pilot
     * @param pilotId - id of the pilot, of who we want to get licenses
     * @return - 200 ok or 404 not found if pilot not found
     */
    @GetMapping
    public ResponseEntity<GetLicensesResponse> getLicenses(@PathVariable("pilotId") int pilotId){
        Optional<Pilot> pilot = pilotService.find(pilotId);
        return pilot.map(value -> ResponseEntity
                .ok(GetLicensesResponse.entityToDtoMapper()
                        .apply(licenseService.findAll(value))))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Get given license of given pilot
     * @param pilotId - id of the pilot
     * @param licenseId - id of the license
     * @return - 200 ok or 404 not found if pilot or license does not exits
     */
    @GetMapping("{licenseId}")
    public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("pilotId") int pilotId,
                                                         @PathVariable("licenseId") int licenseId){
        return licenseService.find(pilotId, licenseId)
                .map(value -> ResponseEntity.ok(GetLicenseResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creating new license same as PostMpaping
     * @param pilotId - id of the pilot we want to give new license
     * @param request - request with data for new license
     * @param builder - URI builder for http response
     * @return 404 not found if pilot with given id does not exist, or created if license
     * was created successfuly
     */
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


    /**
     * Same as PutMapping without additinal valuse
     * @param pilotId - see PutMapping - createLicense
     * @param request - see PutMapping - createLicense
     * @param builder - see PutMapping - createLicense
     * @return - see PutMapping - createLicense
     */
    @PostMapping
    public ResponseEntity<Void> createLicensePOST(@PathVariable("pilotId") int pilotId,
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

    /**
     * Update existing license of given pilot
     * @param pilotId - id of the pilot, who is the license owner
     * @param request - http request body with new data for license
     * @param licenseId - id of the license which we want to update
     * @return - accepted or not found (if license does not exist)
     */
    @PutMapping("{licenseId}")
    public ResponseEntity<Void> updateLicense(@PathVariable ("pilotId") int pilotId,
                                              @RequestBody UpdateLicenseRequest request,
                                              @PathVariable("licenseId") int licenseId){
        Optional<License> licenseToUpdate = licenseService.find(pilotId, licenseId);
        if(licenseToUpdate.isPresent()){
            UpdateLicenseRequest.dtoToEntityUpdater(id -> pilotService.find(id).get()).apply(licenseToUpdate.get(), request);
            licenseService.update(licenseToUpdate.get());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete all licenses of given pilot
     * @param pilotId - id of the pilot
     * @return - accepted or not found
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAllLicenses(@PathVariable("pilotId") int pilotId){
        Optional<Pilot> pilot = pilotService.find(pilotId);
        if(pilot.isPresent()){
            List<License> toDelete = pilot.get().getLicenseList();
            for(License l : toDelete){
                licenseService.delete(l.getId());
            }
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Delete given license from given pilot
     * @param pilotId - id of the pilot
     * @param licenseId - id of the license
     * @return - accepted or not found if license not found
     */
    @DeleteMapping("{licenseId}")
    public ResponseEntity<Void> deleteLicense(@PathVariable("pilotId") int pilotId,
                                              @PathVariable("licenseId") int licenseId){
        Optional<License> license = licenseService.find(pilotId, licenseId);
        if(license.isPresent()){
            licenseService.delete(license.get().getId());
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}
