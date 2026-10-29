public class Driver {
    public static void main(String[] args) {

        Media[] items = {
            new Book("Java Book", 3),
            new DVD("Movie DVD", 2),
            new Game("Video Game", 4)
        };

        double total = 0;

        for (Media m : items) {
            System.out.println(
                m.title + " Late Fee: Rs. " + m.lateFee()
            );

            total += m.lateFee();
        }

        System.out.println("Total Late Fees: Rs. " + total);
    }
}   