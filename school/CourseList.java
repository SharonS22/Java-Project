package school;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the list of courses.
 *
 * @author Yongsen Luo
 */
public class CourseList {
    private final List<Course> courseList;
    private static final CourseList instance = new CourseList();

    private CourseList() {
        courseList = new ArrayList<>();
    }

    void remove(Course course) {
        courseList.remove(course); // Package-restricted
    }

    public static CourseList instance() {
        return instance; // Ensure there is only a single class storing courses with singleton
    }

    public void print() {
        for (Course course : courseList) {
            System.out.println(course.getName() + " with a required qualification in " + course.getRequiredQualification() + ".");
        }
    }

    /**
     * Prints a filtered sublist from the list of courses
     */
    public void print(List<Course> sublist) {
        for (Course course : sublist) {
            System.out.println(course.getName() + " created by " + course.getCourseDirectorName() + ".");
        }
    }

    public void add(Course course) throws Exception {
        courseList.add(course);
    }

    public Course find(String courseName) throws Exception {
        for (Course course : courseList) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        throw new Exception("No such course.");
    }

    public List<Course> filter(String courseDirectorName) {
        // Find courses a particular director added so that they can see them
        // will not be able to see other courses
        List<Course> filtered = new ArrayList<>();
        for (Course course : courseList) {
            if (course.getCourseDirectorName().equals(courseDirectorName)) {
                filtered.add(course);
            }
        }
        return filtered;
    }

    public List<Course> getCourseList() {
        return courseList;
    }
}
