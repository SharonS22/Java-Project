package school;

/**
 * Class to represent the course information, including course's name, course
 * director's name and course qualification.
 *
 * @author Yongsen Luo
 */
public class Course {
    private String name;
    private String courseDirectorName;
    private Qualification requiredQualification;

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, String courseDirectorName, Qualification requiredQualification) {
        this.name = name;
        this.courseDirectorName = courseDirectorName;
        this.requiredQualification = requiredQualification;
    }

    /**
     * Factory method to create a course
     */
    public static Course makeCourse(String name, String courseDirectorName, String requiredQualification) {
        return new Course(name, courseDirectorName, new Qualification(requiredQualification));
    }

    String getCourseDirectorName() {
        return courseDirectorName;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Qualification getRequiredQualification() {
        return requiredQualification;
    }

    /**
     * Display for the simplified database
     * E.g., Software Engineering,Bob Peterson,Software Engineering
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s", name, courseDirectorName, requiredQualification);
    }
}
