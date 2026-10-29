import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {}

class MyTests {
    @Run
    public void testAddition() {
        System.out.println("Addition test passed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Subtraction test passed");
    }

    public void helperMethod() {
        System.out.println("This method should not run");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) throws Exception {
        MyTests tests = new MyTests();
        int count = 0;

        for (Method method : tests.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Run.class)
                    && method.getParameterCount() == 0) {
                method.invoke(tests);
                count++;
            }
        }

        System.out.println("Total tests executed: " + count);
    }
}
