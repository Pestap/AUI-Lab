package eti.pg.lab.pilot.dto;

import eti.pg.lab.pilot.entity.Pilot;
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

public class UpdatePilotRequest {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;

    public static BiFunction<Pilot, UpdatePilotRequest, Pilot> dtoToEntityUpdater(){
        return (pilot, request) -> {
            pilot.setName(request.getName());
            pilot.setSurname(request.getSurname());
            pilot.setDateOfBirth(request.getDateOfBirth());
            return pilot;
        };
    }
}