package school;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the list of teachers.
 *
 * @author Shoumiao Liu
 */
public class TeacherList {
    private final List<Teacher> teacherList;
    private static final TeacherList instance = new TeacherList();

    private TeacherList() {
        teacherList = new ArrayList<>();
    }

    public static TeacherList instance() {
        return instance; // Ensure there is only a single class storing teachers with singleton
    }

    public void print() {
        for (Teacher teacher : teacherList) {
            System.out.println(teacher.getName() + " with qualifications in " + teacher.seeQualifications() + " teaching " + teacher.seeCourses() + " and assigned to " + teacher.seeTrainings() + " training.");
        }
    }

    public void add(Teacher teacher) {
        teacherList.add(teacher);
    }

    /**
     * To find a teacher in the list
     */
    public Teacher find(String teacherName) throws Exception {
        for (Teacher teacher : teacherList) {
            if (teacher.getName().equals(teacherName)) {
                return teacher;
            }
        }
        throw new Exception("No such teacher.");
    }

    public Teacher filter(String qualificationName) throws Exception {
        // Filter out only teachers with proper qualifications
        for (Teacher teacher : teacherList) {
            for (Qualification qualification : teacher.getQualifications()) {
                if (qualification.getQualificationName().equals(qualificationName)) {
                    return teacher;
                }
            }
        }
        throw new Exception("There is no match.");
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }
}
