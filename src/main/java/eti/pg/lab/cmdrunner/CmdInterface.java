package eti.pg.lab.cmdrunner;

import eti.pg.lab.pilot.service.PilotService;
import eti.pg.lab.plane.service.PlaneService;

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
            System.out.println("1. List categories");
            System.out.println("2. List all elements");
            System.out.println("3. Add an element");
            System.out.println("4. Delete an element");
            System.out.println("5. Quit");

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
                System.out.println("The categories are: \n - pilots \n - planes");
            }else if(answer == 2) {
                System.out.println("Planes:");
                planeService.findAll().forEach(System.out::println);
                System.out.println("=================");
                System.out.println("Pilots:");
                pilotService.findAll().forEach(System.out::println);
            }else if(answer == 3) {

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
                //delete from pilots
            }else if(answer == 2){
                //delete from planes
            }else{
                System.out.println("Value to high!");
            }


        }
    }

}
