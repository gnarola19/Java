package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Repository<T> {

    private final HashMap<String, T> storage = new HashMap<>();

    public T save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
    }

    private String extractId(T entity) {
        Class<?> type = entity.getClass();

        while (type != null && type != Object.class) {
            for (Field field : type.getDeclaredFields()) {

                boolean isId = false;

                for (java.lang.annotation.Annotation annotation
                        : field.getAnnotations()) {
                    if (annotation.annotationType().getSimpleName()
                            .equals("Id")) {
                        isId = true;
                        break;
                    }
                }

                if (isId) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);

                        if (value == null) {
                            throw new IllegalArgumentException(
                                    "@Id field cannot be null"
                            );
                        }

                        return value.toString();

                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(
                                "Cannot read @Id field", e
                        );
                    }
                }
            }

            type = type.getSuperclass();
        }

        throw new IllegalArgumentException(
                "No field marked with @Id found in "
                + entity.getClass().getName()
        );
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public int count() {
        return storage.size();
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException(
                    "Array cannot be null or empty"
            );
        }

        double sum = 0;

        for (N value : values) {
            if (value == null) {
                throw new IllegalArgumentException(
                        "Array cannot contain null values"
                );
            }

            sum += value.doubleValue();
        }

        return sum / values.length;
    }
}