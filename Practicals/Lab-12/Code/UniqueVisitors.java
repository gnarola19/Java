import java.util.*;

public class UniqueVisitors {
    public static void main(String[] args) {

        List<String> logins = Arrays.asList(
            "Grishma", "Aarav", "Diya", "Grishma",
            "Krish", "Aarav", "Riya", "Diya"
        );

        // HashSet stores only unique names.
        Set<String> uniqueUsers = new HashSet<>(logins);

        // LinkedHashSet preserves first-seen order.
        Set<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Total unique visitors: " + uniqueUsers.size());

        System.out.println("First three distinct visitors:");

        int count = 0;

        for (String user : orderedUsers) {
            System.out.println(user);
            count++;

            if (count == 3) {
                break;
            }
        }
    }
}