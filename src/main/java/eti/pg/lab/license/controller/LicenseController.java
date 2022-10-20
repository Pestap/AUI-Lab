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

    /**
     * Gets all licences
     * @return OK response
     */
    @GetMapping
    public ResponseEntity<GetLicensesResponse> getLicenses(){
        return ResponseEntity.ok(GetLicensesResponse.entityToDtoMapper().apply(licenseService.findAll()));
    }

    /**
     * Gets a license with provided ID
     * @param id - id of the license we want to get
     * @return - OK response or 404 not found (if license with a given id does not exist)
     */
    @GetMapping("{id}")
    public ResponseEntity<GetLicenseResponse> getLicense(@PathVariable("id") int id){
        return licenseService.find(id)
                .map(value -> ResponseEntity
                        .ok(GetLicenseResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creating a new license - same as POST (we should not use POST)
     * @param request - the HTTP request containing data for new license
     * @param builder - URI builder
     * @return - http response with location header
     */
    @PutMapping
    public ResponseEntity<Void> createLicense(@RequestBody CreateLicenseRequest request, UriComponentsBuilder builder){
        License licenseToAdd = CreateLicenseRequest.dtoToEntityMapper(id -> pilotService.find(id).orElseThrow()).apply(request);
        licenseToAdd = licenseService.create(licenseToAdd);

        return ResponseEntity.created(builder
                .pathSegment("api", "licenses", "{id}")
                .buildAndExpand(licenseToAdd.getId()).toUri())
            .build();

    }

    /**
     * same as PUT mapping with no parameters
     */
    @PostMapping
    public ResponseEntity<Void> createLicenseByPost(@RequestBody CreateLicenseRequest request, UriComponentsBuilder builder){
        License licenseToAdd = CreateLicenseRequest.dtoToEntityMapper(id -> pilotService.find(id).orElseThrow()).apply(request);
        licenseToAdd = licenseService.create(licenseToAdd);

        return ResponseEntity.created(builder
                        .pathSegment("api", "licenses", "{id}")
                        .buildAndExpand(licenseToAdd.getId()).toUri())
                .build();

    }

    /**
     * Updates a license with given id
     * @param request - http request with dataa
     * @param id - id of the license we want to update
     * @return - 200 OK or 404 found if license does not exist
     */
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

    /**
     * Deletes license with a given id
     * @param id - id of the license we want to delete
     * @return = 202 or 404 not found if license does not exist
     */
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
