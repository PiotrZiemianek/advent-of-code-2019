package day5;

import services.IntComputer;

import java.nio.file.Path;
import java.util.List;

import static services.IntComputer.*;

public class Day5 {
    static Path path = Path.of("src/main/resources/day5input.txt");

    public static void main(String[] args) {

        List<Integer> intcode = getIntcode(path);
//        System.out.println(intcode);
        setInput(5);
        processIntcode(intcode);
//        System.out.println(intcode);
        System.out.println(getOutput());

    }
}
