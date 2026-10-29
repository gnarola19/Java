import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class LogAnalyzer {
    public static void main(String[] args) {
        Path folder = Paths.get("logs");
        String keyword = "ERROR";
        int totalLines = 0;
        int matchingLines = 0;

        try {
            Files.createDirectories(folder);

            Files.write(folder.resolve("app1.txt"), java.util.Arrays.asList(
                    "Application started",
                    "ERROR: Connection failed",
                    "User logged in"
            ));

            Files.write(folder.resolve("app2.txt"), java.util.Arrays.asList(
                    "ERROR: File not found",
                    "Application running",
                    "ERROR: Timeout occurred"
            ));

            Files.write(folder.resolve("app3.txt"), java.util.Arrays.asList(
                    "Server started",
                    "Request completed"
            ));

            try (DirectoryStream<Path> stream =
                         Files.newDirectoryStream(folder, "*.txt")) {

                for (Path file : stream) {
                    int fileLines = 0;
                    int fileMatches = 0;

                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;

                        while ((line = reader.readLine()) != null) {
                            fileLines++;

                            if (line.contains(keyword)) {
                                fileMatches++;
                            }
                        }
                    }

                    BasicFileAttributes attributes =
                            Files.readAttributes(file, BasicFileAttributes.class);

                    totalLines += fileLines;
                    matchingLines += fileMatches;

                    System.out.println("\nFile: " + file.getFileName());
                    System.out.println("Lines: " + fileLines);
                    System.out.println("Keyword matches: " + fileMatches);
                    System.out.println("Size: " + attributes.size() + " bytes");
                }
            }

            System.out.println("\n===== FINAL SUMMARY =====");
            System.out.println("Total lines: " + totalLines);
            System.out.println("Lines containing ERROR: " + matchingLines);

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}