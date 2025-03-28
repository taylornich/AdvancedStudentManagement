public class Person {
    private String name;
    private int age;
    private String email;

    public Person(String name, int age, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age must be a positive number.");
        }
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Email: " + email;
    }
}
