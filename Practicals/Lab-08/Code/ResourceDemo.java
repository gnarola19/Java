class BankResource implements AutoCloseable {
    public BankResource() {
        System.out.println("Resource opened.");
    }

    public void process() throws Exception {
        throw new Exception("Transaction failed!");
    }

    @Override
    public void close() {
        System.out.println("Resource closed.");
    }
}

public class ResourceDemo {
    public static void main(String[] args) {
        try (BankResource resource = new BankResource()) {
            resource.process();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}