package eti.pg.lab.cmdrunner;

import eti.pg.lab.CloningUtility;
import eti.pg.lab.pilot.entity.Pilot;
import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.plane.entity.Plane;
import eti.pg.lab.plane.service.PlaneService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CmdInterface {
    private Scanner input;
    private PilotService pilotService;
    private PlaneService planeService;
    private boolean quit;
    public CmdInterface(Scanner in, PilotService pilotService,PlaneService planeService){
        this.input = in;
        this.pilotService = pilotService;
        this.planeService = planeService;
        quit = false;
    }

    public void DisplayUI(){
        /** initial UI state
         *
         */
        System.out.println("CATEGORIES: List categories (pilots)");
        System.out.println("ELEMENTS: List all elements (planes)");
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
            String inputString = input.nextLine();


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
                System.out.println("Planes:");
                planeService.findAll().forEach(System.out::println);
                System.out.println();

            }else if(inputString.equals("ADD")) {
                addItem();
            }else if(inputString.equals("DELETE")){
                deleteItem();
            }else if(inputString.equals("HELP")) {
                System.out.println("CATEGORIES: List categories (pilots)");
                System.out.println("ELEMENTS: List all elements (planes)");
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
            System.out.println("PLANE");
            String inputString = input.nextLine();

            if(inputString.equals("PILOT")){
                deletePilot();
                break;
            }else if(inputString.equals("PLANE")){
                deletePlane();
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

    private void deletePlane(){
        System.out.println("Give the name of a plane you want to delete:");
        String name = input.nextLine();

        /* delete plane from all pilots
         */

        List<Pilot> pilots = pilotService.findAll();

        if(pilots != null) {
            for (int i = 0; i < pilots.size(); i++) {
                List<Plane> planeList = pilots.get(i).getPlaneCertificationList();

                if(planeList != null) {
                    for (int j = 0; j < planeList.size(); j++) {
                        if (planeList.get(j).getTypeName().equals(name)) {
                            planeList.remove(planeList.get(j));
                            /* a new pilot is created based on the old one */

                            Pilot copy = CloningUtility.clone(pilots.get(i));
                            copy.setPlaneCertificationList(planeList);

                            pilotService.delete(copy.getId());
                            pilotService.create(copy);
                        }
                    }
                }
            }
        }

        planeService.delete(name);
    }

    private void addItem(){
        while(true){
            System.out.println("What type of entity would you like to add?");
            System.out.println("PILOT");
            System.out.println("PLANE");
            String inputString = input.nextLine();

            if(inputString.equals("PILOT")){
                addPilot();
                break;
            }else if(inputString.equals("PLANE")){
                addPlane();
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
                    .planeCertificationList(new ArrayList<>())
                    .build();
            try { // in case pilot with that id already exists
                pilotService.create(newPilot);
            }catch(IllegalArgumentException e){
                System.out.println("Pilot already exists! Try again!");
            }
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException ex){
            System.out.println("Invalid data! Try again!");
        }
    }

    private void addPlane(){
        /*
         * get the plane name: can contain space
         */
        System.out.println("Provide the name of the plane:");
        String name = input.nextLine();

        /*
         * get the remaining information about an airplane
         */
        System.out.println("Provide plane information in format:");
        System.out.println("capacity yearOfLaunch monthOLaunch dayOfLaunch");

        /*
        split string into tokens
         */
        String planeInformationString = input.nextLine();
        String[] planeInformation = planeInformationString.split(" ");

        /*
         * Get plane desription (can contain whitespace)
         */

        System.out.println("Provide a short descripiton of the plane:");
        String planeDescritpiton = input.nextLine();

        try { // in case data is invalid (ex. string instead of integer
            Plane newPlane = Plane.builder()
                            .typeName(name)
                            .capacity(Integer.valueOf(planeInformation[0]))
                            .launchDate(LocalDate.of(Integer.valueOf(planeInformation[1]), Integer.valueOf(planeInformation[2]), Integer.valueOf(planeInformation[3])))
                            .description(planeDescritpiton)
                            .build();
            try { //in case a plane with the same name already exists
                planeService.create(newPlane);

                //choose category (pilot)
                System.out.println("Choose the pilot for the plane:");

                int counter =0;

                List<Pilot> pilots = pilotService.findAll();
                for(Pilot p : pilots ){
                    System.out.println(counter + " - " + p);
                    counter++;
                }

                 int pilotIndex = Integer.valueOf(input.nextLine());

                Pilot toAdd = pilots.get(pilotIndex);

                toAdd.getPlaneCertificationList().add(newPlane);

                pilotService.delete(toAdd.getId());
                pilotService.create(toAdd);

            }catch(IllegalArgumentException ex){
                System.out.println("Plane already exists!");
            }
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException ex){
            System.out.println("Invalid data! Try again!");
        }
    }


}
