package eti.pg.lab.license.dto;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetLicenseResponse {
    private int id;
    private String privilegeLevel;
    private LocalDate issueDate;
    private String description;
    private int pilotId;

    public static Function<License, GetLicenseResponse> entityToDtoMapper(){
        return license -> GetLicenseResponse.builder()
                .id(license.getId())
                .privilegeLevel(license.getPrivilegeLevel())
                .issueDate(license.getIssueDate())
                .description(license.getDescription())
                .pilotId(license.getPilot().getId())
                .build();
    }
}
