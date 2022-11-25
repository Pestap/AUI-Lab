package eti.pg.lab.pilot.event.dto;


import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreatePilotRequest {
    private int id;


    public static Function<Pilot, CreatePilotRequest> entityToDtoMapper(){
        return pilot -> CreatePilotRequest.builder().id(pilot.getId()).build();
    }
}
