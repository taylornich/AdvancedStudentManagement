import java.util.*;
import java.util.stream.Collectors;

public class StudentManagement {
    private List<Student> students;
    private List<Teacher> teachers;
    private Scanner scanner;

    public StudentManagement() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        scanner = new Scanner(System.in);
        JDBC.setupDatabase();
        JDBC.fetchStudentsFromDatabase(students);
        JDBC.fetchTeachersFromDatabase(teachers);
    }

    public void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter student grade: ");
        int grade = scanner.nextInt();

        if (students.stream().anyMatch(student -> student.getStudentId() == studentId)) {
            System.out.println("This ID belongs to an existing student.");
        }

        Student student = new Student(name, age, email, studentId, grade);
        students.add(student);
        JDBC.insertStudentIntoDatabase(student);
        System.out.println("Student added successfully.");
    }

    public void addTeacher() {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter teacher email: ");
        String email = scanner.nextLine();
        System.out.print("Enter teacher ID: ");
        int teacherId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter teacher subject: ");
        String subject = scanner.nextLine();

        Teacher teacher = new Teacher(name, age, email, teacherId, subject);
        teachers.add(teacher);
        JDBC.insertTeacherIntoDatabase(teacher);
        System.out.println("Teacher added successfully.");
    }

    public void viewAllStudentsConcurrently() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }

        List<Student> studentsCopy = new ArrayList<>(students);

        int mid = students.size() / 2;
        int task1EndIndex = (students.size() % 2 == 0) ? mid : mid + 1;

        Runnable task1 = () -> {
            System.out.println("\nFirst half of students:");
            for (int i = 0; i < task1EndIndex; i++) {
                System.out.println(studentsCopy.get(i));
            }
        };

        Runnable task2 = () -> {
            System.out.println("\nSecond half of students:");
            for (int i = task1EndIndex; i < students.size(); i++) {
                System.out.println(studentsCopy.get(i));
            }
        };

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void countStudentsByGrade() {
        System.out.print("Enter grade to count students: ");
        int grade = scanner.nextInt();
        long count = students.stream().filter(student -> student.getGrade() == grade).count();
        System.out.println("There are " + count + " students in grade " + grade);
    }

    public void filterAndSortStudents() {
        System.out.print("Enter grade to filter students: ");
        int grade = scanner.nextInt();
        List<Student> filteredStudents = students.stream()
                .filter(student -> student.getGrade() == grade)
                .sorted(Comparator.comparing(Student::getName))
                .collect(Collectors.toList());
        filteredStudents.forEach(student -> System.out.println(student.getName() + " - " + student.getGrade()));
    }

    public void updateStudent() {
        System.out.print("Enter the ID of the student you would like to update: ");
        int studentID = scanner.nextInt();
        scanner.nextLine();

        Student student = findStudentById(studentID);

        if (student != null) {
            System.out.println("Student found: " + student);
            System.out.print("Enter new name or press Enter to keep the same: ");
            String name = scanner.nextLine();
            if (!name.isEmpty())student.setName(name);

            System.out.print("Enter new age or press Enter to keep the same: ");
            String ageStr = scanner.nextLine();
            if (!ageStr.isEmpty())student.setAge(Integer.parseInt(ageStr));

            System.out.print("Enter new email or press Enter to keep the same: ");
            String email = scanner.nextLine();
            if (!email.isEmpty())student.setEmail(email);

            System.out.print("Enter new grade or press Enter to keep the same: ");
            String gradeStr = scanner.nextLine();
            if (!gradeStr.isEmpty())student.setGrade(Integer.parseInt(gradeStr));

            JDBC.updateStudentInDatabase(student);
            System.out.println("Student updated successfully");
        }
        else {
            System.out.print("Student not found.");
        }
    }

    public void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int studentId = scanner.nextInt();

        Student student = findStudentById(studentId);
        if (student != null) {
            students.remove(student);
            JDBC.deleteStudentFromDatabase(studentId);
            System.out.println("Student deleted successfully.");
        }
        else {
            System.out.println("Student not found.");
        }
    }

    private Student findStudentById(int studentId) {
        return students.stream().filter(student -> student.getStudentId() == studentId).findFirst().orElse(null);
    }


    public void displayMenu() {
        while (true) {
            System.out.println("\nStudent Management System Menu");
            System.out.println("1. Add a Student");
            System.out.println("2. Add a Teacher");
            System.out.println("3. View All Students Concurrently");
            System.out.println("4. Export Students to File");
            System.out.println("5. Import Students from File");
            System.out.println("6. Export Teachers to File");
            System.out.println("7. Import Teachers from File");
            System.out.println("8. Count Students by Grade");
            System.out.println("9. Filter and Sort Students by Grade");
            System.out.println("10. Update Student");
            System.out.println("11. Delete Student");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addTeacher();
                    break;
                case 3:
                    viewAllStudentsConcurrently();
                    break;
                case 4:
                    FileManager.exportStudentsToFile(students);
                    break;
                case 5:
                    FileManager.importStudentsFromFile(students);
                    break;
                case 6:
                    FileManager.exportTeachersToFile(teachers);
                    break;
                case 7:
                    FileManager.importTeachersFromFile(teachers);
                    break;
                case 8:
                    countStudentsByGrade();
                    break;
                case 9:
                    filterAndSortStudents();
                    break;
                case 10:
                    updateStudent();
                    break;
                case 11:
                    deleteStudent();
                    break;
                case 12:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        StudentManagement system = new StudentManagement();
        system.displayMenu();
    }
}