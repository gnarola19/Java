import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {
    String name();
}

class Student {
    @Column(name = "student_name")
    String name;

    @Column(name = "student_age")
    int age;

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + "}";
    }
}

public class ColumnDemo {
    public static void main(String[] args) throws Exception {
        String[] headers = {"student_name", "student_age"};
        String[] data = {"Grishma", "19"};

        Student student = new Student();

        for (Field field : Student.class.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column == null) continue;

            int index = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(column.name())) {
                    index = i;
                    break;
                }
            }

            if (index == -1 || index >= data.length) {
                System.out.println("Missing column: " + column.name());
                continue;
            }

            field.setAccessible(true);

            if (field.getType() == String.class) {
                field.set(student, data[index]);
            } else if (field.getType() == int.class) {
                field.setInt(student, Integer.parseInt(data[index]));
            }
        }

        System.out.println(student);
    }
}
