package school;

/**
 * Class to represent the school.Administrator user role
 *
 * @author Katarina Zaprazna
 */
public class Administrator {
    private final TeacherList teacherList;
    private final CourseList courseList;
    private final TrainingList trainingList;

    public Administrator(TeacherList teacherList, CourseList courseList, TrainingList trainingList) {
        this.teacherList = teacherList;
        this.courseList = courseList;
        this.trainingList = trainingList;
    }

    /**
     * Factory method to create an administrator
     */
    public static Administrator makeAdministrator(TeacherList teacherList, CourseList courseList, TrainingList trainingList) {
        return new Administrator(teacherList, courseList, trainingList);
    }

    public Teacher assignTeaching(Course teaching, Teacher teacher) throws Exception {
        for (Course course : teacher.getCoursesAssigned()) {
            if (!(course.getName().equals(teaching.getName()))) { // If they do not already teach it
                teacher.setCourse(teaching); // Assign them to teaching
                return teacher;
            } else {
                throw new Exception("Cannot assign a teaching twice to the same teacher.");
            }
        }
        return teacher;
    }

    public void clearTeaching(Course teaching, Teacher teacher) throws Exception {
        for (Course t : teacher.getCoursesAssigned()) {
            if (t.getName().equals(teaching.getName())) {
                // They are assigned to this teaching, and we can reset it
                teacher.resetCourse(teaching);
                return;
            } else {
                throw new Exception("Cannot clear teaching that has not been assigned to teacher.");
            }
        }
    }

    public void arrangeTraining(Teacher teacher, Training training) throws Exception {
        for (Training t : teacher.getTrainingsAssigned()) {
            if (!(t.getName().equals(training.getName()))) { // If not already on that training
                teacher.setTraining(training);
                teacher.setQualification(training.getQualificationGained());
                return;
            } else {
                throw new Exception("Cannot arrange a training twice to the same teacher.");
            }
        }
    }

    public void cancelTraining(Training training, Teacher teacher) throws Exception {
        for (Training t : teacher.getTrainingsAssigned()) {
            if (t.getName().equals(training.getName())) {
                // They are assigned to this training, and we can reset it
                teacher.resetTraining(training);
                teacher.resetQualification(training.getQualificationGained());
                return;
            } else {
                throw new Exception("Cannot cancel a training a teacher is not assigned to.");
            }
        }
    }
}
