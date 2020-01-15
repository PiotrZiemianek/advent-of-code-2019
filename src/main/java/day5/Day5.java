package day5;


import java.nio.file.Path;
import java.util.List;

import static services.IntComputer.*;

public class Day5 {
    private static Path path = Path.of("src/main/resources/day5input.txt");

    public static void main(String[] args) {

        List<Integer> intcode = getIntcode(path);
        setInput(5);
        processIntcode(intcode);
        System.out.println(getOutput());

    }
}
