import java.util.Scanner;
import java.util.Random;

public class RPSLS {

    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    static int winner(Move a, Move b) {
        if (a == b) {
            return 0;
        }

        boolean wins = switch (a) {
            case ROCK -> b == Move.SCISSORS || b == Move.LIZARD;
            case PAPER -> b == Move.ROCK || b == Move.SPOCK;
            case SCISSORS -> b == Move.PAPER || b == Move.LIZARD;
            case LIZARD -> b == Move.SPOCK || b == Move.PAPER;
            case SPOCK -> b == Move.SCISSORS || b == Move.ROCK;
        };

        return wins ? 1 : -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int computerScore = 0;

        Move[] moves = Move.values();

        for (int i = 1; i <= 5; i++) {
            System.out.println("\nRound " + i);
            System.out.print("Enter ROCK, PAPER, SCISSORS, LIZARD, SPOCK: ");

            Move player = Move.valueOf(sc.next().toUpperCase());
            Move computer = moves[random.nextInt(moves.length)];

            System.out.println("You chose: " + player);
            System.out.println("Computer chose: " + computer);

            int result = winner(player, computer);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            } else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            } else {
                System.out.println("It's a tie!");
            }
        }

        System.out.println("\nFinal Score: You " + playerScore
                + " - " + computerScore + " Computer");

        if (playerScore > computerScore) {
            System.out.println("You win!");
        } else if (computerScore > playerScore) {
            System.out.println("Computer wins!");
        } else {
            System.out.println("Overall tie!");
        }

        sc.close();
    }
}