public class Driver {
    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 8),
            new Circle(3)
        };

        double total = 0;
        double largest = 0;

        for (Shape s : shapes) {
            double a = s.area();

            System.out.printf("%s Area: %.2f%n",
                    s.getClass().getSimpleName(), a);

            total += a;

            if (a > largest) {
                largest = a;
            }
        }

        System.out.printf("Total Area: %.2f%n", total);
        System.out.printf("Largest Area: %.2f%n", largest);
    }
}public class Driver {
    public static void main(String[] args) {

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 8),
            new Circle(3)
        };

        double total = 0;
        double largest = 0;

        for (Shape s : shapes) {
            double a = s.area();

            System.out.printf("%s Area: %.2f%n",
                    s.getClass().getSimpleName(), a);

            total += a;

            if (a > largest) {
                largest = a;
            }
        }

        System.out.printf("Total Area: %.2f%n", total);
        System.out.printf("Largest Area: %.2f%n", largest);
    }
}