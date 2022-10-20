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
public class CreatePilotRequest {
    private String name;
    private String surname;
    private int id;
    private LocalDate dateOfBirth;

    public static Function<CreatePilotRequest, Pilot> dtoToEntityMapper(){
        return request -> Pilot.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .id(request.getId())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }
}
