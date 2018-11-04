
/**
 * This class creates a Cat object
 *
 * @author law37
 * @version 1.2 (23rd March 2018)
 */
public class Cat extends Animal {
    private boolean shareRun;
    /**
     * the cat constructor
     * @param name name of the cat
     * @param favFood favorite food of the animal
     * @param meals number of meals per day
     * @param shareRun can they share a run enclosure
     * @param type type of animal it is
     */
    public Cat(String name, String favFood, int meals, boolean shareRun, String type) {
        super(name, favFood, meals, type);
        this.shareRun = shareRun;
        this.type = "CAT";
    }

    /**
     * checks if the run is shared
     * @return shareRun
     */
    @Override
    public boolean isShareRun() {
        return shareRun;
    }
}
