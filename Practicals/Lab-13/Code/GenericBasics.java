class Box<T> {
    private T value;

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}

public class GenericBasics {

    public static <T> T firstElement(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        return array[0];
    }

    public static void main(String[] args) {

        Box<Integer> integerBox = new Box<>();
        integerBox.set(100);
        System.out.println("Integer Box: " + integerBox.get());

        Box<String> stringBox = new Box<>();
        stringBox.set("MiniBank");
        System.out.println("String Box: " + stringBox.get());

        Pair<String, Integer> student =
                new Pair<>("Aarav", 20);

        System.out.println("Pair Name: " + student.getFirst());
        System.out.println("Pair Age: " + student.getSecond());

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Diya", "Krish", "Aarav"};

        System.out.println("First Number: " + firstElement(numbers));
        System.out.println("First Name: " + firstElement(names));
    }
}