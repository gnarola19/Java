public class CommandParser {

    public static Command parse(String line) {

        if (line == null) {
            throw new IllegalArgumentException(
                    "Command cannot be null"
            );
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Command must contain exactly 3 parts"
            );
        }

        try {
            TransactionType type =
                    TransactionType.valueOf(
                            parts[0].toUpperCase()
                    );

            String accountNumber = parts[1];

            if (!Validator.isValidAmount(parts[2])) {
                throw new IllegalArgumentException(
                        "Amount must be a positive whole number"
                );
            }

            long amount = Long.parseLong(parts[2]);

            return new Command(type, accountNumber, amount);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}