package eti.pg.lab.pilot.dto;

import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;

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
public class GetPilotsResponse {

    @Singular
    private List<Pilot> pilots;

    public static Function<Collection<Pilot>, GetPilotsResponse> entityToDtoMapper(){
        return pilots -> {
                GetPilotsResponseBuilder response = GetPilotsResponse.builder();
                pilots.stream()
                        .map(pilot ->Pilot.builder()
                                .name(pilot.getName())
                                .surname(pilot.getSurname())
                                .id(pilot.getId())
                                .dateOfBirth(pilot.getDateOfBirth())
                                .build())
                        .forEach(response::pilot);
                return response.build();
        };
    }
}
