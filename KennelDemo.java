import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class runs a Kennel
 *
 * @author law37
 * @version 1.2 (23rd March 2018)
 */
public class KennelDemo {
    private String filename; // holds the name of the file
    private Kennel kennel; // holds the kennel
    private Scanner scan; // so we can read from keyboard

    /*
     * Notice how we can make this constructor private, since we only call from main which
     * is in this class. We don't want this class to be used by any other class.
     */
    private KennelDemo() {
        scan = new Scanner(System.in);
        do {
            System.out.print("Please enter the filename of kennel information: ");
            filename = scan.nextLine();
            if (!filename.equals("")) {
                kennel = new Kennel();
            } else {
                System.out.println("Shit not valid mate");
            }

        }while (filename.equals("")) ;
    }

    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            kennel.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + filename + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    admitAnimal();
                    break;
                case "2":
                    changeKennelName();
                    break;
                case "3":
                    printAll();
                    break;
                case "4":
                    searchForAnimal();
                    break;
                case "5":
                    removeAnimal();
                    break;
                case "6":
                    setKennelCapacity();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
                    break;
            }
        } while (!(response.equals("Q")));
    }

    private void printMenu() {
        System.out.println("1 -  admit a new animal");
        System.out.println("2 -  set up Kennel name");
        System.out.println("3 -  display all inmates");
        System.out.println("4 -  search for an animal");
        System.out.println("5 -  remove an animal");
        System.out.println("6 -  set kennel capacity");
        System.out.println("q - Quit");
    }

    private void setKennelCapacity() {
        int max = 0;
        do {
            try {
                System.out.print("Enter max number of dogs: ");
                max = scan.nextInt();
            } catch (InputMismatchException a) {
                System.err.println("You need to enter a number larger than 1");
                setKennelCapacity();
            }
            if (max < 0) {
                System.err.println("Enter a number more than 0");
                setKennelCapacity();
            } else {
                scan.nextLine();
                kennel.setCapacity(max);
            }
        }while(max < 1);
    }

    private void printAll() {
        kennel.obtainAllInmates();
    }

    private void save() {
        try {
            kennel.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }
    }

    private void removeAnimal() {
        System.out.println("which dog do you want to remove");
        String removed;
        removed = scan.nextLine();
        if (removed.equals("")){
            System.err.println("Enter a animal to be removed.");
            removeAnimal();
        }else {
            kennel.removeAnimal(removed);
        }
    }

    private void searchForAnimal() {
        System.out.println("which animal do you want to search for");
        String name = scan.nextLine();
        if (name.equals("")){
            System.out.println("That's not valid!");
            printMenu();
        }
        Animal animal = kennel.search(name);
        if (animal != null) {
            System.out.println(animal.toString());
        } else {
            System.out.println("Could not find animal: " + name);
        }
    }

    private void changeKennelName() {
        System.out.println("Enter a Kennel name: ");
        String name = scan.nextLine();
        if (name.equals("")){
            System.err.println("Enter a string here.");
            changeKennelName();
        }else {
            kennel.setName(name);
        }
    }

    private void admitAnimal() {
        System.out.println("enter on separate lines: name, favourite food, number of times fed, owner-name, owner-phone, ");
        String name = scan.nextLine().toUpperCase();
        while (name.equals("")) {
            System.out.println("Enter a name.");
            name = scan.nextLine();
        }
        System.out.println("What is his/her favourite food?");
        String fav;
        fav = scan.nextLine();
        while (fav.equals("")) {
            System.out.println("Enter a favorite food.");
            fav = scan.nextLine();
        }
        System.out.println("How many times is he/she fed a day? (as a number)");
        String string;
        int numTimes = 0;
        do {
            try {
                string = scan.nextLine();
                numTimes = Integer.parseInt(string); // This can be improved (InputMismatchException?)
                if (numTimes < 0) {
                    System.out.println("Too small a number");
                }
            } catch (NumberFormatException e) {
                System.err.println("Enter a number.");
            }
        } while (numTimes < 0);

        String type;
        do {
            System.out.println("Is it a cat or a dog?");
            type = scan.nextLine().toUpperCase();
            Animal animal = new Animal("Unknown", "Unknown", 1, type);
            switch (type) {
                case "CAT":
                    animal = new Cat(name, fav, numTimes, false, "CAT");
                    System.out.println("Is the cat friendly?");
                    String shareRun;
                    shareRun = scan.nextLine().toUpperCase();
                    if (shareRun.equals("Y")) {
                        animal = new Cat(name, fav, numTimes, true, "CAT");
                    }
                    ArrayList<Owner> owners = getOwners();
                    for (Owner o : owners) {
                        animal.addOriginalOwner(o);
                    }
                    kennel.addAnimal(animal);
                    break;
                case "DOG":
                    animal = new Dog(name, fav, numTimes, false, false, "DOG");
                    System.out.println("Does it like bones? Does it need walks? (Y/N)");
                    String likeBones;
                    String walks;
                    likeBones = scan.nextLine().toUpperCase();
                    walks = scan.nextLine().toUpperCase();
                    if (likeBones.equals("Y") && walks.equals("Y")) {
                        animal = new Dog(name, fav, numTimes, true, true, "DOG");
                    } else if (likeBones.equals("Y")) {
                        animal = new Dog(name, fav, numTimes, true, false, "DOG");
                    } else if (walks.equals("Y")) {
                        animal = new Dog(name, fav, numTimes, false, true, "DOG");
                    }
                    owners = getOwners();
                    for (Owner o : owners) {
                        animal.addOriginalOwner(o);
                    }
                    kennel.addAnimal(animal);
                    break;
                default:
                    System.err.println("That's not allowed");
                    break;
            }
        }while (!type.equals("CAT") && !type.equals("DOG"));
    }

    private ArrayList<Owner> getOwners(){
            ArrayList<Owner> owners = new ArrayList<Owner>();
            String answer;
            do {
                System.out.println("Enter on separate lines: owner-name owner-phone");
                String ownName = scan.nextLine();
                String ownPhone = scan.nextLine();
                Owner own = new Owner(ownName, ownPhone);
                owners.add(own);
                System.out.println("Another owner (Y/N)?");
                answer = scan.nextLine().toUpperCase();
            } while (!answer.equals("N"));
            return owners;
        }


    ////////////////////////////////////////////////////

    /**
     * main function which allows the program to run properly
     * @param args args
     */

    public static void main(String args[]) {
        System.out.println("**********HELLO***********");
        KennelDemo demo = new KennelDemo();
        demo.initialise();
        demo.runMenu();
        demo.printAll();
        // MAKE A BACKUP COPY OF dogsrus.txt JUST IN CASE YOU CORRUPT IT
        demo.save();
        System.out.println("***********GOODBYE**********");
    }
}
