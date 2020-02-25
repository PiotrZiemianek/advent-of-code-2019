package day9;

import services.LongComputer;

import java.nio.file.Path;
import java.util.List;

public class Day9 {
    static Path path = Path.of("src/main/resources/day9input.txt");

    public static void main(String[] args) {
        List<Long> longcode = LongComputer.getLongcode(path);
        LongComputer.setInput(2L);
        LongComputer.processLongcode(longcode);
        System.out.println("Output: " + LongComputer.getOutput());
    }
}
