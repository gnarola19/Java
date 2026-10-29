package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static void generate(Path logsFolder, Path reportFile)
            throws IOException {

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        List<String> details = new ArrayList<>();

        Files.createDirectories(logsFolder);

        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(logsFolder)) {

            for (Path file : stream) {

                if (!Files.isRegularFile(file)
                        || !file.getFileName().toString().endsWith(".log")
                        || Files.size(file) == 0) {
                    continue;
                }

                BasicFileAttributes attrs =
                        Files.readAttributes(file, BasicFileAttributes.class);

                details.add(
                        file.getFileName()
                        + " | Size: " + attrs.size() + " bytes"
                        + " | Last modified: " + attrs.lastModifiedTime()
                );

                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");

                        if (parts.length < 3) {
                            continue;
                        }

                        try {
                            long amount = Long.parseLong(parts[2]);

                            if (amount < 0) {
                                continue;
                            }

                            if (parts[0].equalsIgnoreCase("DEPOSIT")) {
                                totalDeposits += amount;
                            } else if (parts[0].equalsIgnoreCase("WITHDRAW")) {
                                totalWithdrawals += amount;
                            }
                        } catch (NumberFormatException ignored) {
                            // Ignore malformed transaction amounts.
                        }
                    }
                }
            }
        }

        long netChange = totalDeposits - totalWithdrawals;

        Path parent = reportFile.toAbsolutePath().getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(
                reportFile,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {

            writer.write("MINIBANK END-OF-DAY REPORT");
            writer.newLine();
            writer.write("==========================");
            writer.newLine();
            writer.newLine();

            writer.write("Total Deposits: Rs. " + totalDeposits);
            writer.newLine();
            writer.write("Total Withdrawals: Rs. " + totalWithdrawals);
            writer.newLine();
            writer.write("Net Change: Rs. " + netChange);
            writer.newLine();

            writer.newLine();
            writer.write("LOG FILE DETAILS");
            writer.newLine();

            for (String detail : details) {
                writer.write(detail);
                writer.newLine();
            }
        }

        System.out.println("\n===== END-OF-DAY REPORT =====");
        System.out.println("Total Deposits: Rs. " + totalDeposits);
        System.out.println("Total Withdrawals: Rs. " + totalWithdrawals);
        System.out.println("Net Change: Rs. " + netChange);
        System.out.println("Report saved to: " + reportFile.toAbsolutePath());
    }
}