package eti.pg.lab.pilot.entity;

import eti.pg.lab.plane.entity.Plane;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity for pilot. Represents information about particular pilot
 */

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class Pilot implements Serializable {

    /**
     * pilot's name
     */
    private String name;

    /**
     * pilot's surname
     */
    private String surname;


    /**p
     * pilot's unique id
     */

    private int id;

    /**
    Pilot's date of birth
     */

    private LocalDate dateOfBirth;

    /**
    List of planes the pilot is certified to fly
     */

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Plane> planeCertificationList;


}
