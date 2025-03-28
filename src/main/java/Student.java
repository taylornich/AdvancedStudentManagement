public class Student extends Person {
    private int studentId;
    private int grade;

    public Student(String name, int age, String email, int studentId, int grade) {
        super(name, age, email);
        this.studentId = studentId;
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return super.toString() + ", Student ID: " + studentId + ", Grade: " + grade;
    }
}
