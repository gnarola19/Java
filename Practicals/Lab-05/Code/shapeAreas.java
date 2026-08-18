package Code;
abstract class Shape {
    abstract double area();
}

class Circle extends Shape {
    double r;

    Circle(double r) {
        this.r = r;
    }

    double area() {
        return Math.PI * r * r;
    }
}

class Rectangle extends Shape {
    double l, b;

    Rectangle(double l, double b) {
        this.l = l;
        this.b = b;
    }

    double area() {
        return l * b;
    }
}

class Triangle extends Shape {
    double b, h;

    Triangle(double b, double h) {
        this.b = b;
        this.h = h;
    }

    double area() {
        return 0.5 * b * h;
    }
}

public class shapeAreas {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(8, 3),
            new Circle(3)
        };

        double total = 0;
        double largest = 0;

        for (Shape s : shapes) {
            double a = s.area();
            System.out.println("Area: " + a);

            total += a;

            if (a > largest) {
                largest = a;
            }
        }

        System.out.println("Total area: " + total);
        System.out.println("Largest area: " + largest);
    }
}