package eti.pg.lab.license.dto;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetLicensesResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    /**
     * Static class for license entry in GET response
     */
    public static class LicenseEntry{
        private int id;
        private String privilegeLevel;
        private LocalDate issueDate;
        private String description;
        private int pilotId;
    }

    @Singular
    private List<LicenseEntry> licenses;

    /**
     * maps license collection to http response
     * @return function that does the mapping
     */
    public static Function<Collection<License>, GetLicensesResponse> entityToDtoMapper(){
        return licenses -> {
            GetLicensesResponseBuilder response = GetLicensesResponse.builder();
            licenses.stream()
                    .map(license -> LicenseEntry.builder()
                            .id(license.getId())
                            .privilegeLevel(license.getPrivilegeLevel())
                            .issueDate(license.getIssueDate())
                            .description(license.getDescription())
                            .pilotId(license.getPilot().getId())
                            .build()
                    ).forEach(response::license);
            return response.build();
        };
    }
}
