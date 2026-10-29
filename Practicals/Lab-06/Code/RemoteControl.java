interface Switchable {
    void on();
    void off();

    default void toggle() {
        on();
    }
}

class Fan implements Switchable {
    public void on() {
        System.out.println("Fan is ON");
    }

    public void off() {
        System.out.println("Fan is OFF");
    }
}

class Light implements Switchable {
    public void on() {
        System.out.println("Light is ON");
    }

    public void off() {
        System.out.println("Light is OFF");
    }
}

@FunctionalInterface
interface SwitchRule {
    boolean maySwitchOn(Switchable device, int hour);
}

public class RemoteControl {
    public static void main(String[] args) {

        Switchable[] devices = {new Fan(), new Light()};

        for (Switchable device : devices) {
            device.toggle();
        }

        // Anonymous class
        SwitchRule rule1 = new SwitchRule() {
            public boolean maySwitchOn(Switchable device, int hour) {
                return hour >= 6 && hour <= 22;
            }
        };

        // Lambda expression
        SwitchRule rule2 =
                (device, hour) -> hour >= 6 && hour <= 22;

        System.out.println("Anonymous: "
                + rule1.maySwitchOn(devices[0], 10));

        System.out.println("Lambda: "
                + rule2.maySwitchOn(devices[1], 10));
    }
}