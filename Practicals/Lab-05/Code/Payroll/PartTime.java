public class PartTime extends Employee {
    private int hours;
    private double rate;

    public PartTime(String name, int id, int hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    @Override
    double monthlySalary() {
        return hours * rate;
    }
}