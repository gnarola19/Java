public class Driver {

    public static void main(String[] args) {

        String[] passwords = {
            "abc",
            "abcdefgh",
            "Abcd1234!",
            "password1"
        };

        for (String pw : passwords) {
            PasswordChecker.checkPassword(pw);
        }
    }
}