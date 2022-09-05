package school;

/**
 * Class to representing the qualification of all the teachers.
 *
 * @author Sharon Shaji
 */
public class Qualification {
    private String qualificationName;

    void setQualificationName(String qualification) {
        this.qualificationName = qualification;
    }

    public Qualification(String qualification) {
        this.qualificationName = qualification;
    }

    public String getQualificationName() {
        return qualificationName;
    }

    @Override
    public String toString() {
        return qualificationName;
    }
}
