package eti.pg.lab.license.entity;

import eti.pg.lab.pilot.entity.Pilot;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name="licenses")
/**
 * Entity for plane type.
 */

public class License implements Serializable {
    /**
     * License unique ID
     */
    @Id
    @Column(unique = true, name="license_id")
    private int licenseId;


    /**
     * Privilege level - student, sport, itp
     */
    @Column(name="privilege_level")
    private String privilegeLevel;

    /**
     * Date of license issue
     */
    @Column(name="issue_date")
    private LocalDate issueDate;

    /**
     * short description of the aircraft type
     */

    private String description;

    @ManyToOne
    @JoinColumn(name="pilot")
    private Pilot pilot;

}
