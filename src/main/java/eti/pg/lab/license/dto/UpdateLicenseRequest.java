package eti.pg.lab.license.dto;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateLicenseRequest {
    private String privilegeLevel;
    private LocalDate issueDate;
    private String description;
    private int pilotId;

    public static BiFunction<License, UpdateLicenseRequest, License> dtoToEntityUpdater(
            Function<Integer, Pilot> pilotFunction
    ){
        return (license, request) -> {
            license.setPrivilegeLevel(request.getPrivilegeLevel());
            license.setIssueDate(request.getIssueDate());
            license.setDescription(request.getDescription());
            license.setPilot(pilotFunction.apply(request.getPilotId()));
            return license;
        };
    }
}
