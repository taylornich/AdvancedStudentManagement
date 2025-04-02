import java.io.*;
import java.sql.PreparedStatement;
import java.util.List;

public class FileManager {

    public static void exportStudentsToFile(List<Student> students) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Student student : students) {
                writer.write(student.getStudentId() + "," + student.getName() + "," + student.getAge() + "," + student.getEmail() + "," + student.getGrade());
                writer.newLine();
            }
            System.out.println("Students data exported to students.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportTeachersToFile(List<Teacher> teachers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("teachers.txt"))) {
            for (Teacher teacher : teachers) {
                writer.write(teacher.getTeacherId() + "," + teacher.getName() + "," + teacher.getAge() + "," + teacher.getEmail() + "," + teacher.getSubject());
                writer.newLine();
            }
            System.out.println("Teachers data exported to teachers.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importStudentsFromFile(List<Student> students) {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int studentId = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String email = data[3];
                int grade = Integer.parseInt(data[4]);

                if (!JDBC.studentExistsInDatabase(studentId)) {
                    Student newStudent = new Student(name, age, email, studentId, grade);
                    JDBC.insertStudentIntoDatabase(newStudent);
                }
                else {
                    System.out.println("Student with ID " + studentId + " already exists.");
                }
            }
            System.out.println("Students data imported from students.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importTeachersFromFile(List<Teacher> teachers) {
        try (BufferedReader reader = new BufferedReader(new FileReader("teachers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int teacherId = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String email = data[3];
                String subject = data[4];
                if (!JDBC.teacherExistsInDatabase(teacherId)) {
                    Teacher newTeacher = new Teacher(name, age, email, teacherId, subject);
                    JDBC.insertTeacherIntoDatabase(newTeacher);
                }
                else {
                    System.out.println("Teacher with ID " + teacherId + " already exists.");
                }
            }
            System.out.println("Teachers data imported from teachers.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
