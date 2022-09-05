package school;

/**
 * Class to represent all the trainings and the qualification gained through it.
 *
 * @author Sharon Shaji
 */
public class Training {
    private String name;
    private Qualification qualificationGained;

    public Training(String name) {
        this.name = name;
    }

    public Training(String name, Qualification qualificationGained) {
        this.name = name;
        this.qualificationGained = qualificationGained;
    }

    /**
     * Factory method to create a training
     */
    public static Training makeTraining(String name, String qualificationGained) {
        return new Training(name, new Qualification(qualificationGained));
    }

    void setName(String name) {
        this.name = name;
    }

    Qualification getQualificationGained() {
        return qualificationGained;
    }

    public String getName() {
        return name;
    }

    /**
     * Display for the simplified database
     * E.g., Computer Networking,Computer Networking
     */
    @Override
    public String toString() {
        return String.format("%s,%s", name, qualificationGained);
    }
}
