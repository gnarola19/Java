import java.util.*;

public class Leaderboard {
    public static void main(String[] args) {

        Map<String, Integer> scores = new HashMap<>();

        scores.put("Grishma", 95);
        scores.put("Aarav", 88);
        scores.put("Diya", 95);
        scores.put("Krish", 76);
        scores.put("Riya", 88);
        scores.put("Meera", 91);

        List<Map.Entry<String, Integer>> players =
                new ArrayList<>(scores.entrySet());

        // Sort by score descending, then name ascending.
        players.sort(
            Comparator.comparing(Map.Entry<String, Integer>::getValue)
                      .reversed()
                      .thenComparing(Map.Entry::getKey)
        );

        int topN = 3;

        System.out.println("TOP " + topN + " PLAYERS");
        System.out.println("----------------");

        for (int i = 0; i < Math.min(topN, players.size()); i++) {
            Map.Entry<String, Integer> player = players.get(i);

            System.out.println(
                (i + 1) + ". " + player.getKey()
                + " - " + player.getValue()
            );
        }
    }
}