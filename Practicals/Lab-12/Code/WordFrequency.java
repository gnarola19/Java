import java.util.*;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "java is easy java is powerful java is popular";

        Map<String, Integer> frequency = new HashMap<>();

        String[] words = paragraph.toLowerCase().split("\\W+");

        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(frequency.entrySet());

        entries.sort(
            Comparator.comparing(Map.Entry<String, Integer>::getValue)
                      .reversed()
                      .thenComparing(Map.Entry::getKey)
        );

        System.out.println("Word Frequency (Highest First):");

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}