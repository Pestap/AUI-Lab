package eti.pg.lab.pilot.entity;

import eti.pg.lab.license.entity.License;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    /**
    List of planes the pilot is certified to fly
     */

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pilot")
    @ToString.Exclude
    private List<License> licenseList;



}
