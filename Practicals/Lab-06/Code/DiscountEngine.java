import java.util.Scanner;

@FunctionalInterface
interface DiscountRule {
    double apply(double price);
}

public class DiscountEngine {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double[] prices = {1000, 2000, 3000};

        DiscountRule[] rules = {
            price -> price,
            price -> price * 0.90,
            price -> price * 0.80
        };

        System.out.println("1. No Discount");
        System.out.println("2. 10% Discount");
        System.out.println("3. 20% Discount");
        System.out.print("Choose discount: ");

        int choice = sc.nextInt();

        if (choice < 1 || choice > 3) {
            System.out.println("Invalid choice");
            sc.close();
            return;
        }

        double total = 0;

        for (double price : prices) {
            double result = rules[choice - 1].apply(price);

            System.out.printf("Original: %.2f, Final: %.2f%n",
                    price, result);

            total += result;
        }

        System.out.printf("Total: %.2f%n", total);

        sc.close();
    }
}