package school;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the course director user role.
 *
 * @author Yongsen Luo
 */
public class CourseDirector {
    private final List<Course> coursesCreated;

    public CourseDirector() {
        coursesCreated = new ArrayList<>();
    }

    /**
     * Factory method to create a course director
     */
    public static CourseDirector makeCourseDirector() {
        return new CourseDirector();
    }

    public void createCourse(Course course, CourseList courseList) throws Exception {
        if (coursesCreated.size() == 0) {
            coursesCreated.add(course);
            courseList.add(course); // Simply add to our database
        } else {
            for (Course c : coursesCreated) {
                if (!(c.getName().equals(course.getName()))) { // They have not yet added it
                    coursesCreated.add(course);
                    courseList.add(course); // Add to our database
                    return;
                } else {
                    throw new Exception("Cannot add the same course twice.");
                }
            }
        }
    }

    public void removeCourse(Course course, CourseList courseList) throws Exception {
        for (Course c : coursesCreated) {
            if ((c.getName().equals(course.getName()))) { // A valid course they added previously
                coursesCreated.remove(course);
                courseList.remove(course); // Remove from our database
                return;
            } else {
                throw new Exception("Cannot remove a non-existing course.");
            }
        }
    }
}