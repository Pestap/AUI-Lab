package eti.pg.lab.license.dto;

import eti.pg.lab.license.entity.License;
import lombok.*;

import java.time.LocalDate;
import java.util.function.BiFunction;

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

    public static BiFunction<License, UpdateLicenseRequest, License> dtoToEntityUpdater(){
        return (license, request) -> {
            license.setPrivilegeLevel(request.getPrivilegeLevel());
            license.setIssueDate(request.getIssueDate());
            license.setDescription(request.getDescription());
            return license;
        };
    }
}
