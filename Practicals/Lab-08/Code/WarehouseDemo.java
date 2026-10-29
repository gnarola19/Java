import java.util.*;

class OutOfStockException extends Exception {
    private final int shortfall;

    public OutOfStockException(int shortfall) {
        super("Insufficient stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private final Map<String, Integer> stock = new HashMap<>();

    public Warehouse() {
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 3);
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity must be greater than zero."
            );
        }

        int available = stock.getOrDefault(item, 0);

        if (qty > available) {
            throw new OutOfStockException(qty - available);
        }

        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item + "(s).");
    }
}

public class WarehouseDemo {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();

        String[][] requests = {
            {"Laptop", "2"},
            {"Mouse", "15"},
            {"Keyboard", "0"},
            {"Monitor", "3"}
        };

        for (String[] request : requests) {
            try {
                warehouse.issue(request[0], Integer.parseInt(request[1]));
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("All stock requests processed.");
    }
}