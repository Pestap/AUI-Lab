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

        while(!quit) {
            System.out.println("CATEGORIES: List categories (pilots)");
            System.out.println("ELEMENTS: List all elements (planes)");
            System.out.println("ADD: Add an element");
            System.out.println("DELETE: Delete an element");
            System.out.println("HELP: List all commands");
            System.out.println("QUI: Quit application");

            String inputString = input.nextLine();
            int answer = 0;
            try{
                Integer.parseInt(inputString);
                answer = Integer.valueOf(inputString);
            }
            catch (NumberFormatException ex){
                System.out.println("Invalid input, please enter an integer");
                continue;
            }

            if(answer == 1){
                System.out.println("Pilots:");
                pilotService.findAll().forEach(System.out::println);
                System.out.println();
            }else if(answer == 2) {
                System.out.println("Planes:");
                planeService.findAll().forEach(System.out::println);
                System.out.println();

            }else if(answer == 3) {
                addItem();
            }else if(answer == 4){
                deleteItem();
            }else if(answer == 5){
                this.quit = true;
            }else{
                System.out.println("Value to high!");
            }
        }
    }

    private void deleteItem(){
        while(true){
            int answer = -1;
            System.out.println("From wich category would you like to delete?");
            System.out.println("1. Pilots");
            System.out.println("2. Planes");
            String inputString = input.nextLine();

            try{
                Integer.parseInt(inputString);
                answer = Integer.valueOf(inputString);
            }catch (NumberFormatException ex){
                continue;
            }

            if(answer == 1){
                deletePilot();
                break;
            }else if(answer == 2){
                deletePlane();
                break;
            }else {
                System.out.println("Value to high!");
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
                continue;
            }
        }
        pilotService.delete(answer);
    }

    private void deletePlane(){
        System.out.println("Give the name of a plane you want to delete:");
        String name = input.nextLine();

        /** delete plane from all pilots
         */
        System.out.println("TU JESZCZE DZIALAM");

        List<Pilot> pilots = pilotService.findAll();

        if(pilots != null) {
            for (int i = 0; i < pilots.size(); i++) {
                List<Plane> planeList = pilots.get(i).getPlaneCertificationList();
                if(planeList != null) {
                    for (int j = 0; j < planeList.size(); j++) {
                        if (planeList.get(j).equals(name)) {
                            planeList.remove(planeList.get(j));
                            /** konieczna podmiana pilota
                             */
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
            int answer = -1;
            System.out.println("To which category would you like to add an element?");
            System.out.println("1. Pilots");
            System.out.println("2. Planes");
            String inputString = input.nextLine();

            try{
                Integer.parseInt(inputString);
                answer = Integer.valueOf(inputString);
            }catch (NumberFormatException ex){
                continue;
            }

            if(answer == 1){
                addPilot();
                break;
            }else if(answer == 2){
                addPlane();
                break;
            }else {
                System.out.println("Value to high!");
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
                    .build();
            try {
                pilotService.create(newPilot);
            }catch(IllegalArgumentException e){
                System.out.println("Pilot already exists! Try again!");
            }
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException ex){
            System.out.println("Invalid data! Try again!");
        }
    }

    private void addPlane(){
        System.out.println("Provide the name of the plane:");
        String name = input.nextLine();
        System.out.println("Provide plane information in format:");
        System.out.println("capacity yearOfLaunch monthOLaunch dayOfLaunch");


        String planeInformationString = input.nextLine();
        String[] planeInformation = planeInformationString.split(" ");

        System.out.println("Provide a short descripiton of the plane:");
        String planeDescritpiton = input.nextLine();
        try {
            Plane newPlane = Plane.builder()
                            .typeName(name)
                            .capacity(Integer.valueOf(planeInformation[0]))
                            .launchDate(LocalDate.of(Integer.valueOf(planeInformation[1]), Integer.valueOf(planeInformation[2]), Integer.valueOf(planeInformation[3])))
                            .description(planeDescritpiton)
                            .build();
            try {
                planeService.create(newPlane);
            }catch(IllegalArgumentException ex){
                System.out.println("Plane already exists!");
            }
        }catch(ArrayIndexOutOfBoundsException | NumberFormatException ex){
            System.out.println("Invalid data! Try again!");
        }
    }


}
