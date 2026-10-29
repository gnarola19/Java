import java.util.Scanner;

public class TollBooth {

    record Vehicle(String number, String type) {}

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int total = 0;
        int bike = 0, car = 0, truck = 0;

        while (true) {
            System.out.print("Enter vehicle number (done to stop): ");
            String number = sc.next();

            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = sc.next().toLowerCase();

            Vehicle v = new Vehicle(number, type);

            int toll = switch (v.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> -1;
            };

            if (toll == -1) {
                System.out.println("Invalid vehicle type!");
                continue;
            }

            total += toll;

            switch (v.type()) {
                case "bike" -> bike++;
                case "car" -> car++;
                case "truck" -> truck++;
            }
        }

        System.out.println("Total toll: " + total);

        String frequent;

        if (bike >= car && bike >= truck) {
            frequent = "bike";
        } else if (car >= truck) {
            frequent = "car";
        } else {
            frequent = "truck";
        }

        System.out.println("Most frequent: " + frequent);

        sc.close();
    }
}