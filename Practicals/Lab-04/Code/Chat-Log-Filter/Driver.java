import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] logs = {
            "10:05 alice Hello there",
            "10:06 bob Good morning",
            "10:07 riya hello everyone",
            "InvalidLine"
        };

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        ChatFilter.filter(logs, keyword);

        sc.close();
    }
}