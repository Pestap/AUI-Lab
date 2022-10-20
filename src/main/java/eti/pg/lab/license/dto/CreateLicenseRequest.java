package eti.pg.lab.license.dto;

import eti.pg.lab.license.entity.License;
import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateLicenseRequest {
    private int id;
    private String privilegeLevel;
    private LocalDate issueDate;
    private String description;
    private int pilotId;

    public static Function<CreateLicenseRequest, License> dtoToEntityMapper(
            Function<Integer, Pilot> pilotFunction
    ){
        return request -> License.builder()
                .id(request.getId())
                .privilegeLevel(request.getPrivilegeLevel())
                .issueDate(request.getIssueDate())
                .description(request.getDescription())
                .pilot(pilotFunction.apply(request.getPilotId()))
                .build();
    }
}
