public class Teacher extends Person {
    private int teacherId;
    private String subject;

    public Teacher(String name, int age, String email, int teacherId, String subject) {
        super(name, age, email);
        this.teacherId = teacherId;
        this.subject = subject;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return super.toString() + ", Teacher ID: " + teacherId + ", Subject: " + subject;
    }
}
