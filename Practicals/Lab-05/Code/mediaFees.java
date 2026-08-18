package Code;
abstract class Media {
    String title;
    int daysLate;

    Media(String title, int daysLate) {
        this.title = title;
        this.daysLate = daysLate;
    }

    abstract double lateFee();
}

class Book extends Media {
    Book(String title, int daysLate) {
        super(title, daysLate);
    }

    double lateFee() {
        return daysLate * 2;
    }
}

class Movie extends Media {
    Movie(String title, int daysLate) {
        super(title, daysLate);
    }

    double lateFee() {
        return daysLate * 5;
    }
}

class Game extends Media {
    Game(String title, int daysLate) {
        super(title, daysLate);
    }

    double lateFee() {
        return daysLate * 8;
    }
}

public class mediaFees {
    public static void main(String[] args) {
        Media[] items = {
            new Book("Java Basics", 3),
            new Movie("Avengers", 2),
            new Game("Minecraft", 4),
            new Book("Data Structures", 5)
        };

        double total = 0;
        
        for (Media m : items) {
            double fee = m.lateFee();

            System.out.println(m.title + " - " + fee);

            total += fee;
        }

        System.out.println("Total late fees: " + total);
    }
}