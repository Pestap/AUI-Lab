package eti.pg.lab.license.entity;

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

public class License implements Serializable {
    /**
     * License unique ID
     */

    private int licenseId;


    /**
     * Privilege level - student, sport, itp
     */

    private String privilegeLevel;

    /**
     * Date of license issue
     */

    private LocalDate issueDate;

    /**
     * short description of the aircraft type
     */

    private String description;

}
