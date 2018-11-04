import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * This class runs a Kennel
 *
 * @author law37
 * @version 1.2 (23rd March 2018)
 */


public class Animal implements Comparable<Animal>{
    String name;
    ArrayList<Owner> originalOwners;
    String favFood;
    int foodPerDay;
    String type;

    /**
     * the constructor for the animal
     * @param animalName the name of the animal
     * @param food the favorite food of the animal
     * @param mealsPerDay how many meals per day the animal has
     * @param type the type of animal the creature is
     */
    public Animal(String animalName, String food,
                  int mealsPerDay, String type){
        this.name = animalName;
        originalOwners = new ArrayList<>();
        this.favFood = food;
        this.foodPerDay = mealsPerDay;
        this.type = type;
    }

    /**
     * compares the names of all the animals
     * @param animal the animal which is compared
     * @return the comparison
     */
    public int compareTo(Animal animal){
        return this.name.compareToIgnoreCase(animal.name);
    }

    /**
     * gets the name of the animal
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * makes a new array of owners and adding the current ones
     * @return result
     */
    public Owner[] getOriginalOwners(){
        Owner[] result = new Owner[originalOwners.size()];
        result = originalOwners.toArray(result);
        return result;
    }

    /**
     * checks the status of share run
     * @return shareRun
     */
    public boolean isShareRun() {
        return false;
    }

    /**
     * Checks if the dog can walk
     * @return walk
     */
    public boolean isWalk(){
        return false;
    }

    /**
     * the dog likes bones or not?
     * @return likesBones
     */
    public boolean isLikesBones(){
        return false;
    }

    /**
     * To add an original owner
     * @param o An original owner
     */
    void addOriginalOwner(Owner o){
        originalOwners.add(o);
    }

    /**
     *Saves the animal to the file - cat or dog
     * @param pw printWriter
     */
    void save(PrintWriter pw){
        pw.println(name);
        pw.println(originalOwners.size());
        for (Owner o : originalOwners) {
            pw.println(o.getName());
            pw.println(o.getPhone());
        }
        pw.println(foodPerDay);
        pw.println(favFood);
        pw.println(type);
        if(type.equals("CAT")){
            pw.println(this.isShareRun());
        }else{
            pw.println(this.isWalk());
            pw.println(this.isLikesBones());
        }
    }

    /**
     * loads the animals into the program from a filename specified from the user
     * @param infile takes input
     * @return cat or dog
     */
    public static Animal load(Scanner infile){
        ArrayList <Owner> originalOwner = new ArrayList<>();
        String name = infile.next();
        int numOwners = infile.nextInt();
        for (int oCount = 0; oCount < numOwners; oCount++) {
            String oName = infile.next();
            String phone = infile.next();
            Owner owner = new Owner(oName, phone);
            originalOwner.add(owner);
        }
        int foodPerDay = infile.nextInt();
        String favFood = infile.next();
        String type = infile.next();

        if(type.equals("CAT")){
            boolean shareRun = infile.nextBoolean();
            Cat cat = new Cat (name, favFood, foodPerDay, shareRun, type);
            cat.originalOwners = originalOwner;
            return cat;
        }else{
            boolean walks = infile.nextBoolean();
            boolean likesBones = infile.nextBoolean();
            Dog dog = new Dog(name, favFood, foodPerDay, walks, likesBones, type);
            dog.originalOwners = originalOwner;
            return dog;
        }
    }

    /**
     * uses string builder to print the array in a nice format
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Name: ");
        string.append(name);
        string.append("\n");
        string.append("Original owners are: ");
        string.append(originalOwners);
        string.append("\n");
        string.append("The favourite food is: ");
        string.append(favFood);
        string.append("\n");
        string.append("They get fed ");
        string.append(foodPerDay);
        string.append(" times per day.");
        string.append("\n");

        if(type.toUpperCase().equals("CAT")){
            string.append("Can they share a run? ");
            string.append(isShareRun());
            string.append("\n");
        }else{
            string.append("They have to have walks: ");
            string.append(isWalk());
            string.append("\n");
            string.append("They like bones: ");
            string.append(isLikesBones());
            string.append("\n");
        }
        return (string.toString());
    }
}
