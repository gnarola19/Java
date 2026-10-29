public class DVD extends Media {

    public DVD(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double lateFee() {
        return lateDays * 5.0;
    }
}