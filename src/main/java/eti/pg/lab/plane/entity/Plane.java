package eti.pg.lab.plane.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

/**
 * Entity for plane type.
 */

public class Plane implements Serializable {
    /**
     * Plane type model designation
     */

    private String typeName;


    /**
     * information about passenger+crew capacity of a plane type
     */

    private int capacity;

    /**
     * Date of first flight of this type of aircraft
     */

    private LocalDate launchDate;

    /**
     * short description of the aircraft type
     */

    private String description;

}
