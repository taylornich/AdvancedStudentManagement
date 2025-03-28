import java.sql.*;
import java.util.List;

public class JDBC {
    private static Connection conn;

    public static void setupDatabase() {
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            Statement stmt = conn.createStatement();
            String createStudentTableQuery = "CREATE TABLE IF NOT EXISTS students (studentId INT PRIMARY KEY, name VARCHAR(255), age INT, email VARCHAR(255), grade INT)";
            String createTeacherTableQuery = "CREATE TABLE IF NOT EXISTS teachers (teacherId INT PRIMARY KEY, name VARCHAR(255), age INT, email VARCHAR(255), subject VARCHAR(255))";
            stmt.execute(createStudentTableQuery);
            stmt.execute(createTeacherTableQuery);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertStudentIntoDatabase(Student student) {
        try {
            String query = "INSERT INTO students (studentId, name, age, email, grade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, student.getStudentId());
            stmt.setString(2, student.getName());
            stmt.setInt(3, student.getAge());
            stmt.setString(4, student.getEmail());
            stmt.setInt(5, student.getGrade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertTeacherIntoDatabase(Teacher teacher) {
        try {
            String query = "INSERT INTO teachers (teacherId, name, age, email, subject) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, teacher.getTeacherId());
            stmt.setString(2, teacher.getName());
            stmt.setInt(3, teacher.getAge());
            stmt.setString(4, teacher.getEmail());
            stmt.setString(5, teacher.getSubject());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void fetchStudentsFromDatabase(List<Student> students) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");
            while (rs.next()) {
                int studentId = rs.getInt("studentId");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                int grade = rs.getInt("grade");
                students.add(new Student(name, age, email, studentId, grade));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchTeachersFromDatabase(List<Teacher> teachers) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM teachers");
            while (rs.next()) {
                int teacherId = rs.getInt("teacherId");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String email = rs.getString("email");
                String subject = rs.getString("subject");
                teachers.add(new Teacher(name, age, email, teacherId, subject));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
