abstract class Media {
    protected String title;
    protected int lateDays;

    public Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = Math.max(0, lateDays);
    }

    abstract double lateFee();
}