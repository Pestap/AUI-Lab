package eti.pg.lab.pilot.dto;

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
public class GetPilotResponse {
    private String name;
    private String surname;
    private int id;
    private LocalDate dateOfBirth;

    public static Function<Pilot, GetPilotResponse> entityToDtoMapper(){
        return pilot -> GetPilotResponse.builder()
                .name(pilot.getName())
                .surname(pilot.getSurname())
                .id(pilot.getId())
                .dateOfBirth(pilot.getDateOfBirth())
                .build();
    }
}
