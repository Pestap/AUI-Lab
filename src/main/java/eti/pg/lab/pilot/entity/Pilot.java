package eti.pg.lab.pilot.entity;

import eti.pg.lab.license.entity.License;
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

    @EqualsAndHashCode.Exclude
    private List<License> licenseList;

    @Override
    public String toString(){
        String result = "";
        result = "Pilot: " + surname +" " + name+ ", date ofr birth: " + dateOfBirth + ", id: " + id;
        for(License l : licenseList){
            result+="\n - " + l;
        }
        return result;
    }


}
