public class Book extends Media {

    public Book(String title, int lateDays) {
        super(title, lateDays);
    }

    @Override
    double lateFee() {
        return lateDays * 2.0;
    }
}