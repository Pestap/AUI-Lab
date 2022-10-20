package eti.pg.lab.license.controller;


import eti.pg.lab.license.dto.GetLicenseResponse;
import eti.pg.lab.license.dto.GetLicensesResponse;
import eti.pg.lab.license.service.LicenseService;
import eti.pg.lab.pilot.controller.PilotController;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return licenseService.find(pilo)

    }
}
