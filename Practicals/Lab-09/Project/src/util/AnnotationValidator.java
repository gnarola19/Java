package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();

        if (obj == null) {
            return new String[]{"Object cannot be null"};
        }

        Class<?> current = obj.getClass();

        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive annotation = field.getAnnotation(Positive.class);

                        if (value instanceof Number
                                && ((Number) value).doubleValue() <= 0) {
                            errors.add(annotation.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength annotation = field.getAnnotation(MaxLength.class);

                        if (value instanceof String
                                && ((String) value).length() > annotation.value()) {
                            errors.add(field.getName()
                                    + " exceeds maximum length "
                                    + annotation.value());
                        }
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Unable to validate field: " + field.getName());
                }
            }

            current = current.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}