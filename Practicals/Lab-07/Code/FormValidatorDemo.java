import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class SignupForm {
    @NotBlank
    @MaxLength(30)
    String name;

    @NotBlank
    @MaxLength(50)
    String email;

    SignupForm(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

class FormValidator {
    static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                String value = (String) field.get(obj);
                if (field.isAnnotationPresent(NotBlank.class)
                        && (value == null || value.trim().isEmpty())) {
                    errors.add(field.getName() + " must not be blank");
                }

                MaxLength max = field.getAnnotation(MaxLength.class);
                if (max != null && value != null && value.length() > max.value()) {
                    errors.add(field.getName() + " exceeds maximum length " + max.value());
                }
            } catch (IllegalAccessException e) {
                errors.add("Validation failed");
            }
        }

        return errors;
    }
}

public class FormValidatorDemo {
    public static void main(String[] args) {
        SignupForm form = new SignupForm("", "thisisaverylongemailaddress@example.com");
        List<String> errors = FormValidator.validate(form);

        System.out.println("Validation Errors:");
        for (String error : errors) {
            System.out.println(error);
        }
        System.out.println("Total Errors: " + errors.size());
    }
}
