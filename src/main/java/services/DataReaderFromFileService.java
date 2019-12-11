package services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class DataReaderFromFileService {
    public static Optional<List<String>> read(Path path) {
        Optional<List<String>> lines = Optional.empty();
        try {
            lines = Optional.of(Files.readAllLines(path));
        } catch (IOException e) {
            System.out.println("Nie wczytano pliku.");
        }
        return lines;
    }
}
