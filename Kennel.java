
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * To model a Kennel - a collection of dogs
 *
 * @author law37
 * @version 3.1 (23th March 2018)
 */
public class Kennel {
    private ArrayList<Animal> animals;
    private int nextFreeLocation;
    private int capacity;
    private String name;

    /**
     * Creates a kennel with a default size 20
     */
    public Kennel() {
        this(20);
    }

    /**
     * Create a kennel
     * @param maxNoDogs The capacity of the kennel
     */
    public Kennel(int maxNoDogs) {
        name = "Default Kennel";
        nextFreeLocation = 0; // no Dogs in collection at start
        capacity = maxNoDogs;
        animals = new ArrayList<Animal>(capacity); // set up default. This can
        // actually be exceeded
        // when using ArrayList but we
        // won't allow that
        // to happen.
    }

    /**
     * This method sets the value for the name attribute. The purpose of the
     * attribute is: The name of the kennel e.g. "DogsRUs"
     *
     * @param theName the name of the creature
     */
    public void setName(String theName) {
        name = theName;
    }

    /**
     * Set the size of the kennel
     *
     * @param capacity The max dogs we can house
     */
    public void setCapacity(int capacity) {
        // This should really check to see if we already have dogs
        // in the kennel and reducing the capacity would lead to evictions!
        this.capacity = capacity;
    }

    /**
     * This method returns the number of dogs in a kennel
     *
     * @return int Current number of dogs in the kennel
     */
    public int getNumOfDogs() {
        return nextFreeLocation;
    }

    /**
     * Enables a user to add a Dog to the Kennel
     *
     * @param animal A new animal to home
     */
    public void addAnimal(Animal animal) {
        if (nextFreeLocation >= capacity) {
            System.out.println("Sorry kennel is full - cannot add team");
            return;
        }
        // we add in the position indexed by nextFreeLocation
        // This starts at zero
        animals.add(animal);

        // now increment index ready for next one
        nextFreeLocation = nextFreeLocation + 1;
    }

    /**
     * Enables a user to delete a Dog from the Kennel.
     *
     * @param who The dog to remove
     */
    public void removeAnimal(String who) {
        Animal which = null;
        // Search for the animal by name
        for (Animal a : animals) {
            if (who.equals(a.getName())) {
                which = a;
            }
        }
        if (which != null) {
            animals.remove(which); // Requires that Dog has an equals method
            System.out.println("removed " + who);
            nextFreeLocation = nextFreeLocation - 1;
        } else {
            System.err.println("cannot remove - not in kennel");
        }
    }

    /**
     * @return String showing all the information in the kennel
     */
    @Override
    public String toString() {
        String results = "Data in Kennel " + name + " is: \n";
        for (Animal a : animals) {
            results = a.toString() + "\n";
        }
        return results;
    }

    /**
     * Returns an array of the inmates in the kennels
     */
    public void obtainAllInmates() {
        sort();
        System.out.println(animals);
    }

    /**
     * Searches for and returns the inmate, if found
     * @param name The name of the inmate
     * @return The inmate or else null if not found
     */
    public Animal search(String name) {
        Animal result = null;
        for(Animal animal: animals){
            if(animal.getName().equals(name)){
                return animal;
            }
        }
        return result;
    }
    /**
     * Reads in Kennel information from the file
     *
     * @param infileName The file to read from
     * @throws FileNotFoundException if file doesn't exist
     * @throws IOException if some other IO error occurs
     * @throws IllegalArgumentException if infileName is null or empty
     */
    public void load(String infileName) throws IOException{
        // Using try-with-resource. We will cover this in workshop 15, but
        // what it does is to automatically close the file after the try / catch ends.
        // This means we don't have to worry about closing the file.
        try (FileReader fr = new FileReader(infileName);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a nextInt or nextBoolean
            infile.useDelimiter("\r?\n|\r");

            name = infile.next();
            capacity = infile.nextInt();

            int numAnimals = infile.nextInt();
            for (int i = 0; i < numAnimals; i++) {
                Animal animal = Animal.load(infile);
                this.addAnimal(animal);
            }
        }
    }

    /**
     * Saves the kennel information
     *
     * @param filename The file to save to
     * @throws IOException If some IO error occurs
     */
    public void save(String filename) throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw);) {

            outfile.println(name);
            outfile.println(capacity);
            outfile.println(this.getNumOfDogs());
            for (Animal a : animals) {
                a.save(outfile);
            }
        }
    }

    /**
     * uses Collections to sort the array of animals
     */
    public void sort(){
        Collections.sort(animals);
    }
}
