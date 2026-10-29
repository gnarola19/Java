import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;

public class FolderTreeReport {
    public static void main(String[] args) {
        Path root = Paths.get("sample-folder");
        Path report = Paths.get("folder-report.txt");

        try {
            Files.createDirectories(root.resolve("documents"));
            Files.createDirectories(root.resolve("images"));

            Files.write(root.resolve("notes.txt"),
                    java.util.Arrays.asList("Java NIO report", "Folder traversal"));

            Files.write(root.resolve("documents").resolve("assignment.txt"),
                    java.util.Arrays.asList("Practical 11", "File handling"));

            Files.write(root.resolve("images").resolve("info.txt"),
                    java.util.Arrays.asList("Sample file"));

            try (java.io.BufferedWriter writer = Files.newBufferedWriter(report)) {
                writer.write("FOLDER TREE REPORT");
                writer.newLine();
                writer.write("==================");
                writer.newLine();

                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(
                            Path file, BasicFileAttributes attrs) throws IOException {

                        BasicFileAttributes details =
                                Files.readAttributes(file, BasicFileAttributes.class);

                        String name = root.relativize(file).toString();

                        String line = "Name: " + name
                                + " | Size: " + details.size() + " bytes"
                                + " | Modified: "
                                + Instant.ofEpochMilli(
                                        details.lastModifiedTime().toMillis());

                        writer.write(line);
                        writer.newLine();

                        System.out.println(line);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(
                            Path file, IOException error) throws IOException {
                        writer.write("Could not read: " + file + " - "
                                + error.getMessage());
                        writer.newLine();
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            System.out.println("\nReport saved to: " + report.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}