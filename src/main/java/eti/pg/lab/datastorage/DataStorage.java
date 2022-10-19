package eti.pg.lab.datastorage;

import eti.pg.lab.CloningUtility;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.license.entity.License;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataStorage
{
    /**
     * Set of all unique plane types
     */

    private Set<License> licenses = new HashSet<>();

    /**
     * Set of all pilots
     */

    private Set<Pilot> pilots = new HashSet<>();

    /**
     * Seeks for all planes
     * @return - returns a deep copy of planeTypes set
     */

    public synchronized List<License> findAllLicenses(){
        return new ArrayList<>(licenses);
    }

    /**
     *
     * @param id - license id
     * @return - container (can be empty) with planeType if it is present
     */
    public synchronized Optional<License> findLicense(int id){
        return licenses.stream().filter(type -> type.getLicenseId() == id).findFirst().map(CloningUtility::clone);
    }

    /**
     * Adds a new plane type to set
     * @param license - specification of the plane
     * @throws IllegalArgumentException - if the plane type already exists
     */

    public synchronized void addLicense(License license) throws IllegalArgumentException{
        findLicense(license.getLicenseId()).ifPresentOrElse(
                typeNameFound -> {
                    throw new IllegalArgumentException("Plane type: " + license.getLicenseId()
                            + " already exists!");
                }, ()-> licenses.add(CloningUtility.clone(license))
                // choose pilot to add the plane to
        );
    }

    /**
     * delets a plane type from collection
     * @param id - id of the license we want to delete
     * @throws IllegalArgumentException - if the plane type does not exist in storage
     */
    public synchronized void deleteLicense(int id) throws IllegalArgumentException{
        findLicense(id).ifPresentOrElse(
                planeFound -> licenses.remove(planeFound),
                () ->{ throw new IllegalArgumentException("License: " + id + " does not exist!");}
        );
    }

    /**
     * Seeks for all pilots
     * @return - returns a deep copy of pilots set
     */

    public synchronized List<Pilot> findAllPilots(){
        return new ArrayList<>(pilots);
    }

    /**
     *
     * seeks for a pilot
     * @param id - pilot's unique id
     * @return - pilot entity if it exists (or empty optional if it doesnt)
     */

    public synchronized Optional<Pilot> findPilot(int id){
        return pilots.stream()
                .filter(pilot -> pilot.getId() == id)
                .findFirst()
                .map(CloningUtility::clone);
    }

    /**
     * adds pilot to collection
     * @param pilot - the pilot we want to add
     * @throws IllegalArgumentException - if pilot already exists (based on id)
     */

    public synchronized void addPilot(Pilot pilot) throws IllegalArgumentException{
        findPilot(pilot.getId()).ifPresentOrElse(
                pilotFound -> {throw new IllegalArgumentException("Pilot with id: " + pilot.getId() + " already exists!");},
                () -> pilots.add(CloningUtility.clone(pilot))
        );
    }

    /**
     * removes pilots from collection
     * @param id - id of the pilot we want removed
     * @throws IllegalArgumentException - if the id does not exist
     */

    public void deletePilot(int id) throws IllegalArgumentException{
        findPilot(id).ifPresentOrElse(
                pilotFound -> pilots.remove(pilotFound),
                () -> {throw new IllegalArgumentException("Pilot id: " + id + " does not exist!" );}
        );
    }



}
