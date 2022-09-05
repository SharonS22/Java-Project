import school.*;

import java.io.*;
import java.util.*;

/**
 * Class to represent the main application logic
 *
 * @author Katarina Zaprazna, Shoumiao Liu
 */
public class App {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        // Initialise core objects
        TeacherList teacherList = TeacherList.instance();
        CourseList courseList = CourseList.instance();
        TrainingList trainingList = TrainingList.instance();
        Administrator administrator = Administrator.makeAdministrator(teacherList, courseList, trainingList);
        CourseDirector courseDirector = CourseDirector.makeCourseDirector();

        // Initialise the database and login
        read("database.txt", teacherList, trainingList, courseList);
        boolean isAdmin = login();

        /**
         * Main app loop
         */
        while (true) {
            showCommands(isAdmin); // Display commands
            String[] command = parse();
            if (!permissionsCorrect(command[0], isAdmin)) continue; // Handle permissions
            // Dispatch logic between the different commands
            switch (command[0]) {
                case "logout" -> logout("database.txt", teacherList, trainingList, courseList);
                case "assign" -> {
                    // assign,<course_name>
                    // assign,Advanced Data Analytics
                    try {
                        Course course = courseList.find(command[1]);
                        Teacher teacher = teacherList.filter(course.getRequiredQualification().getQualificationName()); // Find by qualification
                        administrator.assignTeaching(course, teacher);
                        System.out.println("Success! " + teacher.getName() + " was assigned to teach " + course.getName() + ".");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "clear" -> {
                    // clear,<course_name>,<teacher_name>
                    // clear,Advanced Data Analytics,Ian Anderson
                    try {
                        Course course = courseList.find(command[1]);
                        Teacher teacher = teacherList.find(command[2]);
                        administrator.clearTeaching(course, teacher);
                        System.out.println("Success! " + teacher.getName() + " no longer teaches " + command[1] + ".");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "arrange" -> {
                    // arrange,<teacher_name>,<qualification_required>
                    // arrange,Ian Anderson,Computer Networking
                    try {
                        Teacher teacher = teacherList.find(command[1]);
                        Training training = trainingList.filter(command[2]);
                        administrator.arrangeTraining(teacher, training); //todo
                        System.out.println("Success! " + teacher.getName() + " was allocated to a " + training.getName() + " training.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "cancel" -> {
                    // cancel,<teacher_name>,<training_name>
                    // cancel,Ian Anderson,Computer Networking
                    try {
                        Teacher teacher = teacherList.find(command[1]);
                        Training training = trainingList.filter(command[2]); // cancel just from them
                        administrator.cancelTraining(training, teacher);
                        System.out.println("Success! " + teacher.getName() + " no longer attends " + command[2] + " training.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "create" -> {
                    // create,<course_name>,<your_name>,<qualification_required>
                    // create,Databases Theory & Applications,Catherine Holland,Database Fundamentals
                    Qualification qualification = new Qualification(command[3]);
                    Course course = new Course(command[1], command[2], qualification);
                    try {
                        courseDirector.createCourse(course, courseList);
                        System.out.println("Success! " + course.getName() + " was added to the database.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "remove" -> {
                    // remove,<course_name>
                    // remove,Databases Theory & Applications
                    try {
                        Course course = courseList.find(command[1]);
                        courseDirector.removeCourse(course, courseList);
                        System.out.println("Success! " + command[1] + " was removed from the database.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "see courses for" -> {
                    // see courses for,<your_name>
                    // see courses for,Catherine Holland
                    List<Course> courses = courseList.filter(command[1]); // Filter by course director
                    courseList.print(courses);
                }
                case "see all courses" ->
                        // see all courses
                        courseList.print();
                case "see all teachers" ->
                        // see all teachers
                        teacherList.print();
                case "see all trainings" ->
                        // see all trainings
                        trainingList.print();
            }
        }
    }

    private static String[] parse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your choice:");
        return scanner.nextLine().split(",");
    }

    private static boolean permissionsCorrect(String command, boolean isAdmin) {
        Set<String> permissionsC = new TreeSet<>(Set.of("see courses for", "create", "remove", "logout"));
        Set<String> permissionsA = new TreeSet<>(Set.of("assign", "clear", "arrange", "cancel", "see all courses", "see all teachers", "see all trainings", "logout"));
        if (!permissionsC.contains(command) && !permissionsA.contains(command)) { // If an unknown command, say that it is invalid
            System.out.println("Invalid command. Try again");
            return false;
        } else if (!permissionsC.contains(command) && !isAdmin) {
            System.out.println("You do not have the permission to issue this command!");
            return false;
        } else if (!permissionsA.contains(command) && isAdmin) {
            System.out.println("You do not have the permission to issue this command!");
            return false;
        } else {
            return true;
        }
    }

    private static boolean login() {
        System.out.println("-------------------------------------------------");
        System.out.println("WELCOME TO PTT MANAGEMENT SYSTEM MADE BY GROUP 19");
        System.out.println("-------------------------------------------------");
        System.out.println("Type <a> to login as Administrator or <cd> to login as Course Director.\n");
        return parse()[0].equals("a");
    }

    private static void logout(String fileName, TeacherList teacherList, TrainingList trainingList, CourseList courseList) {
        write(fileName, teacherList, trainingList, courseList); // Write everything into the database
        System.out.println("You are logged out.");
        System.exit(0);
    }

    private static void showCommands(boolean isAdmin) {
        if (isAdmin) showAdminCommands();
        else showCourseDirectorCommands();
    }

    private static void showAdminCommands() {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("To logout type: logout");
        System.out.println("*To assign teaching type: assign,<course_name>");
        System.out.println("To clear teaching type: clear,<course_name>,<teacher_name>");
        System.out.println("To arrange teacher training type: arrange,<teacher_name>,<qualification_required>");
        System.out.println("To cancel teacher training type: cancel,<teacher_name>,<training_name>");
        System.out.println("To see all courses type: see all courses");
        System.out.println("To see all teachers type: see all teachers");
        System.out.println("To see all trainings type: see all trainings");
        System.out.println("*Our system automatically matches a qualified teacher with the given course.");
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    private static void showCourseDirectorCommands() {
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        System.out.println("To logout type: logout");
        System.out.println("To create a new course type: create,<course_name>,<your_name>,<qualification_required>");
        System.out.println("To remove an existing course type: remove,<course_name>");
        System.out.println("To see courses you created type: see courses for,<your_name>");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }

    private static void read(String fileName, TeacherList teacherList, TrainingList trainingList, CourseList courseList) {
        FileReader fr = null;
        int lineCount = 0;
        // Try reading from the database
        try {
            fr = new FileReader(fileName);
            Scanner s = new Scanner(fr);
            while (s.hasNextLine()) { // Iterate over the entire file
                String line = s.nextLine();
                lineCount++;
                String[] tokens = line.split(",");
                if (lineCount < 4) {
                    // Teacher block
                    // Ian Anderson,Data Analytics,Q2,C1,C2,T1
                    Teacher teacher = Teacher.makeTeacher(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                    teacherList.add(teacher);
                } else if (lineCount < 7) {
                    // Training block
                    // Computer Networking,Computer Networking
                    Training training = Training.makeTraining(tokens[0], tokens[1]);
                    trainingList.add(training);
                } else {
                    // Course block
                    // Data Analytics, Catherine Holland, Data Analytics
                    Course course = Course.makeCourse(tokens[0], tokens[1], tokens[2]);
                    courseList.add(course);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void write(String fileName, TeacherList teacherList, TrainingList trainingList, CourseList courseList) {
        PrintWriter pw = null;
        // Try writing to the database
        try {
            pw = new PrintWriter(fileName);
            pw.print("");//make DB blank
            for (Teacher t : teacherList.getTeacherList()) {
                pw.println(t.toString());
            }
            for (Training t : trainingList.getTrainingList()) {
                pw.println(t.toString());
            }
            for (Course c : courseList.getCourseList()) {
                pw.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}