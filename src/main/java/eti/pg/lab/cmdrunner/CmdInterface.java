package eti.pg.lab.cmdrunner;

import eti.pg.lab.CloningUtility;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.license.entity.License;
import eti.pg.lab.license.service.LicenseService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdInterface {
    private Scanner input;
    private PilotService pilotService;
    private LicenseService licenseService;
    private boolean quit;
    public CmdInterface(Scanner in, PilotService pilotService, LicenseService licenseService){
        this.input = in;
        this.pilotService = pilotService;
        this.licenseService = licenseService;
        quit = false;
    }

    public void DisplayUI(){
        /** initial UI state
         *
         */
        System.out.println("CATEGORIES: List categories (pilots)");
        System.out.println("ELEMENTS: List all elements (license)");
        System.out.println("ADD: Add an element");
        System.out.println("DELETE: Delete an element");
        System.out.println("HELP: List all commands");
        System.out.println("QUIT: Quit application");

        /** main UI loop
         *
         */
        while(!quit) {
            /**
             * get command
             */
            String inputString = input.nextLine().toUpperCase();


            if(inputString.equals("CATEGORIES")){
                /**List all categories
                 *
                 */
                System.out.println("Pilots:");
                pilotService.findAll().forEach(System.out::println);
                System.out.println();
            }else if(inputString.equals("ELEMENTS")) {
                /**
                 * list all elements
                 */
                System.out.println("Licenses:");
                licenseService.findAll().forEach(System.out::println);
                System.out.println();

            }else if(inputString.equals("ADD")) {
                addItem();
            }else if(inputString.equals("DELETE")){
                deleteItem();
            }else if(inputString.equals("HELP")) {
                System.out.println("CATEGORIES: List categories (pilots)");
                System.out.println("ELEMENTS: List all elements (licenses)");
                System.out.println("ADD: Add an element");
                System.out.println("DELETE: Delete an element");
                System.out.println("HELP: List all commands");
                System.out.println("QUIT: Quit application");
            }else if(inputString.equals("QUIT")){
                this.quit = true;
            }else{
                /** invalid command handling
                 *
                 */
                System.out.println("Command not found. Type HELP to list avaliable commands.");
            }
        }
    }

    private void deleteItem(){
        while(true){
            System.out.println("From which type of entity would you like to delete would you like to delete?");
            System.out.println("PILOT");
            System.out.println("LICENSE");
            String inputString = input.nextLine();
            // ignore case
            inputString = inputString.toUpperCase();
            if(inputString.equals("PILOT")){
                deletePilot();
                break;
            }else if(inputString.equals("LICENSE")){
                deleteLicense();
                break;
            }else {
                System.out.println("Unknown entity type!");
            }
        }
    }

    private void deletePilot(){
        int answer = 0;
        while(true){
            System.out.println("Give the id of pilot you want to delete:");
            String inputString = input.nextLine();

            try{
                Integer.parseInt(inputString);
                answer = Integer.valueOf(inputString);
                break;
            }catch (NumberFormatException ex) {
                System.out.println("Given id is not valid!");
            }
        }
        pilotService.delete(answer);
    }

    private void deleteLicense(){
        System.out.println("Give the id of license you want to delete:");
        try{
            // get input
            String idString = input.nextLine();
            int id = Integer.valueOf(idString);
            /* delete plane from all pilots
             */

            List<Pilot> pilots = pilotService.findAll();

            if(pilots != null) {
                for (int i = 0; i < pilots.size(); i++) {
                    List<License> licenseList = pilots.get(i).getLicenseList();

                    if(licenseList != null) {
                        for (int j = 0; j < licenseList.size(); j++) {
                            if (licenseList.get(j).getLicenseId() == id) {
                                licenseList.remove(licenseList.get(j));
                                /* a new pilot is created based on the old one */

                                Pilot copy = CloningUtility.clone(pilots.get(i));
                                copy.setLicenseList(licenseList);

                                pilotService.delete(copy.getId());
                                pilotService.create(copy);
                            }
                        }
                    }
                }
            }

            licenseService.delete(id);
        }catch(NumberFormatException ex){
            System.out.println("Invalid id! Try again!");
        }

    }

    private void addItem(){
        while(true){
            System.out.println("What type of entity would you like to add?");
            System.out.println("PILOT");
            System.out.println("LICENSE");
            String inputString = input.nextLine();
            // ignore case
            inputString = inputString.toUpperCase();

            if(inputString.equals("PILOT")){
                addPilot();
                break;
            }else if(inputString.equals("LICENSE")){
                addLicense();
                break;
            }else {
                System.out.println("Unknown entity type!");
            }
        }
    }

    private void addPilot(){
        System.out.println("Provide pilot information in format:");
        System.out.println("name surname id yearOfBirth monthOfBirth dayOfBirth");

        String pilotInformationString = input.nextLine();
        String[] pilotInformation = pilotInformationString.split(" ");
        try {
            Pilot newPilot = Pilot.builder()
                    .name(pilotInformation[0])
                    .surname(pilotInformation[1])
                    .id(Integer.valueOf(pilotInformation[2]))
                    .dateOfBirth(LocalDate.of(Integer.valueOf(pilotInformation[3]), Integer.valueOf(pilotInformation[4]), Integer.valueOf(pilotInformation[5])))
                    .licenseList(new ArrayList<>())
                    .build();
            // in case pilot with that id already exists
            pilotService.create(newPilot);
        }catch(ArrayIndexOutOfBoundsException | IllegalArgumentException ex){
            System.out.println("Invalid data or pilot with a given id already exists! Try again!");
        }
    }

    private void addLicense(){


        try { // in case data is invalid (ex. string instead of integer
            /*
             * get the remaining information about an airplane
             */
            System.out.println("Provide license information in the following format:");
            System.out.println("licenseId privilegeLevel yearOfIssue monthOfIssue dayOfIssue");

            /*
            split string into tokens
             */
            String licenseInformationString = input.nextLine();
            String[] licenseInformation = licenseInformationString.split(" ");

            /*
             * Get license desription (can contain whitespace)
             */

            System.out.println("Provide a short descripiton of the license:");
            String licenseDescription = input.nextLine();

            License newLicense = License.builder()
                            .licenseId(Integer.valueOf(licenseInformation[0]))
                            .privilegeLevel(licenseInformation[1])
                            .issueDate(LocalDate.of(Integer.valueOf(licenseInformation[2]), Integer.valueOf(licenseInformation[3]), Integer.valueOf(licenseInformation[4])))
                            .description(licenseDescription)
                            .build();


            //choose category (pilot)
            System.out.println("Choose pilot for license:");

            int counter =0;

            List<Pilot> pilots = pilotService.findAll();
            for(Pilot p : pilots ){
                System.out.println(counter + " - " + p);
                counter++;
            }

             int pilotIndex = Integer.valueOf(input.nextLine());

            Pilot toAdd = pilots.get(pilotIndex);

            toAdd.getLicenseList().add(newLicense);

            pilotService.delete(toAdd.getId());
            pilotService.create(toAdd);
            licenseService.create(newLicense);
        }catch(ArrayIndexOutOfBoundsException | IllegalArgumentException ex){
            System.out.println("Invalid data provided or license already exists! Try again!");
        }
    }


}
