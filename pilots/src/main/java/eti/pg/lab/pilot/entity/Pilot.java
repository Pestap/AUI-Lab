package eti.pg.lab.pilot.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity for pilot. Represents information about particular pilot
 */

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Entity
@Table(name="pilots")
public class Pilot implements Serializable {

    /**
     * pilot's name
     */

    @Column(name = "pilot_name")
    private String name;

    /**
     * pilot's surname
     */
    @Column(name="pilot_surname")
    private String surname;


    /**p
     * pilot's unique id
     */
    @Id
    @Column(unique = true)
    private int id;

    /**
    Pilot's date of birth
     */
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
}
