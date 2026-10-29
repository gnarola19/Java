class GenericStack<T> {
    private Object[] elements;
    private int top;

    public GenericStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        elements = new Object[capacity];
        top = -1;
    }

    public void push(T item) {
        if (top == elements.length - 1) {
            throw new IllegalStateException("Stack is full");
        }

        elements[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == -1) {
            throw new IllegalStateException("Stack is empty");
        }

        T item = (T) elements[top];
        elements[top--] = null;
        return item;
    }

    public boolean isEmpty() {
        return top == -1;
    }
}

public class GenericStackDemo {
    public static void main(String[] args) {

        GenericStack<Integer> numbers = new GenericStack<>(5);
        numbers.push(10);
        numbers.push(20);

        System.out.println("Integer popped: " + numbers.pop());

        GenericStack<String> names = new GenericStack<>(5);
        names.push("Aarav");
        names.push("Diya");

        System.out.println("String popped: " + names.pop());

        System.out.println("Integer stack empty: " + numbers.isEmpty());

        GenericStack<String> emptyStack = new GenericStack<>(3);

        try {
            emptyStack.pop();
        } catch (IllegalStateException e) {
            System.out.println("Empty stack handled: " + e.getMessage());
        }
    }
}