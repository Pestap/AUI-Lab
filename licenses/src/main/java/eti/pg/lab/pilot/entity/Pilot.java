package eti.pg.lab.pilot.entity;

import eti.pg.lab.license.entity.License;
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

    /**p
     * pilot's unique id
     */
    @Id
    @Column(unique = true)
    private int id;

    /**
    List of planes the pilot is certified to fly
     */

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pilot",cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<License> licenseList;



}
