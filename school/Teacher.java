package school;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a teacher in the system.
 *
 * @author Shoumiao Liu, Katarina Zaprazna
 */
public class Teacher {
    private String name;
    private List<Qualification> qualifications;
    private List<Training> trainingsAssigned;
    /**
     * Use of ArrayList to store training data for future extensibility, to allow the application to allocate many trainings to teachers.
     * Although we now restrict the number of trainings to 1 only to make it work with the simple textual database, we recognise
     * the need to allow teachers being allocated to more than one as it better models the real life. This will also allow for an easy integration of the project
     * with a real database if needed, which makes no assumptions about how many pieces of data can be stored there, as does the ArrayList.
     */
    private List<Course> coursesAssigned;

    public Teacher(String name) {
        this.name = name;
        qualifications = new ArrayList<>();
        trainingsAssigned = new ArrayList<>();
        coursesAssigned = new ArrayList<>();
    }

    /**
     * Factory method to create a teacher
     */
    public static Teacher makeTeacher(String name, String q1, String q2, String c1, String c2, String t1) {
        Teacher teacher = new Teacher(name);
        teacher.qualifications.add(new Qualification(q1));
        teacher.qualifications.add(new Qualification(q2));
        teacher.coursesAssigned.add(new Course(c1));
        teacher.coursesAssigned.add(new Course(c2));
        teacher.trainingsAssigned.add(new Training(t1));
        return teacher;
    }

    /**
     * Finds a specific course in the list of courses a teacher teaches
     */
    private int findCourse(String courseName) throws Exception {
        for (int i = 0; i < coursesAssigned.size(); i++) {
            if (coursesAssigned.get(i).getName().equals(courseName)) {
                return i;
            }
        }
        throw new Exception("A teacher cannot teach more than two courses.");
    }

    private int findQualification(String qualificationName) throws Exception {
        for (int i = 0; i < qualifications.size(); i++) {
            if (qualifications.get(i).getQualificationName().equals(qualificationName)) {
                return i;
            }
        }
        throw new Exception("A teacher cannot have more than two qualifications.");
    }

    private int findTraining(String trainingName) throws Exception {
        for (int i = 0; i < trainingsAssigned.size(); i++) {
            if (trainingsAssigned.get(i).getName().equals(trainingName)) {
                return i;
            }
        }
        throw new Exception("A teacher cannot be assigned to more than one training.");
    }

    void setQualification(Qualification qualification) throws Exception {
        int index = findQualification("_"); // Find blank to be able to assign
        qualifications.set(index, qualification);
    }

    void resetQualification(Qualification qualification) throws Exception {
        int index = findQualification(qualification.getQualificationName());
        qualifications.get(index).setQualificationName("_"); // Set it to blank again
    }

    void setCourse(Course course) throws Exception {
        int index = findCourse("_"); // Find blank to be able to assign
        coursesAssigned.set(index, course);
    }

    void resetCourse(Course course) throws Exception {
        int index = findCourse(course.getName());
        coursesAssigned.get(index).setName("_"); // Set it to blank again
    }

    void setTraining(Training training) throws Exception {
        int index = findTraining("_"); // Find blank to be able to assign
        trainingsAssigned.set(index, training);
    }

    void resetTraining(Training training) throws Exception {
        int index = findTraining(training.getName());
        trainingsAssigned.get(index).setName("_"); // Set it to blank again
    }

    List<Qualification> getQualifications() {
        return qualifications;
    }

    List<Training> getTrainingsAssigned() {
        return trainingsAssigned;
    }

    List<Course> getCoursesAssigned() {
        return coursesAssigned;
    }

    /**
     * Create a string version of all the qualifications a teacher has, for the UI display
     */
    String seeQualifications() {
        StringBuilder qualificationsString = new StringBuilder();
        for (Qualification qualification : qualifications) {
            qualificationsString.append(qualification.getQualificationName()).append(",");
        }
        return qualificationsString.substring(0, qualificationsString.length() - 1);
    }

    /**
     * Create a string version of all the trainings a teacher has been allocated to, for the UI display
     */
    String seeTrainings() {
        StringBuilder trainingString = new StringBuilder();
        for (Training training : trainingsAssigned) {
            trainingString.append(training.getName()).append(",");
        }
        return trainingString.substring(0, trainingString.length() - 1);
    }

    /**
     * Create a string version of all the courses a teacher teaches, for the UI display
     */
    String seeCourses() {
        StringBuilder courseString = new StringBuilder();
        for (Course course : coursesAssigned) {
            courseString.append(course.getName()).append(",");
        }
        return courseString.substring(0, courseString.length() - 1);
    }

    public String getName() {
        return name;
    }

    /**
     * Display for the simplified database
     * E.g., Alice Tomlinson,Human-Computer Interaction,_,_,_,_
     */
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", name, seeQualifications(), seeCourses(), seeTrainings());
    }
}
