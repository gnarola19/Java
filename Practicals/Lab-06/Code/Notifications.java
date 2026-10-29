@FunctionalInterface
interface Notifier {
    void send(String message);
}

interface Urgent {
}

class EmailNotifier implements Notifier, Urgent {
    public void send(String message) {
        System.out.println("EMAIL: " + message);
    }
}

class SMSNotifier implements Notifier {
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

public class Notifications {
    public static void main(String[] args) {

        Notifier email = new EmailNotifier();
        Notifier sms = new SMSNotifier();

        Notifier[] senders = {email, sms};

        String message = "Transaction completed";

        System.out.println("--- All Notifications ---");

        for (Notifier n : senders) {
            n.send(message);
        }

        System.out.println("--- Urgent Notifications ---");

        for (Notifier n : senders) {
            if (n instanceof Urgent) {
                n.send("URGENT: " + message);
            }
        }
    }
}