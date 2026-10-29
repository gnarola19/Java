import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    int rollNo;
    String name;
    transient String password;

    Student(int rollNo, String name, String password) {
        this.rollNo = rollNo;
        this.name = name;
        this.password = password;
    }

    public String toString() {
        return "Roll No: " + rollNo
                + ", Name: " + name
                + ", Password: " + password;
    }
}

public class SerializationDemo {
    public static void main(String[] args) {
        Student[] students = {
            new Student(1, "Aarav", "pass123"),
            new Student(2, "Diya", "pass456"),
            new Student(3, "Krish", "pass789")
        };

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(students);
            System.out.println("Students saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("students.dat"))) {
            Student[] loaded = (Student[]) in.readObject();

            System.out.println("\nReloaded students:");
            for (Student student : loaded) {
                System.out.println(student);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }
}