package eti.pg.lab.pilot.dto;

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
public class GetPilotsResponse {


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class PilotEntry{
        private String name;
        private String surname;
        private LocalDate dateOfBirth;
        private int numberOfLicenses;
    }
    @Singular
    private List<PilotEntry> pilots;

    public static Function<Collection<Pilot>, GetPilotsResponse> entityToDtoMapper(){
        return pilots -> {
                GetPilotsResponseBuilder response = GetPilotsResponse.builder();

                pilots.stream()
                        .map(pilot -> PilotEntry.builder()
                                .name(pilot.getName())
                                .surname(pilot.getSurname())
                                .dateOfBirth(pilot.getDateOfBirth())
                                .numberOfLicenses(pilot.getLicenseList().size())
                                .build())
                        .forEach(response::pilot);
                return response.build();
        };
    }
}
