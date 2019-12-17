package day2;

import services.IntComputer;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day2 {
    static Path path = Path.of("src/main/resources/day2input.txt");

    public static void main(String[] args) {
        //test
        List<Integer> testIntcode = Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99);
        IntComputer.processIntcode(testIntcode);
        System.out.println(testIntcode);
        System.out.println("30, 1, 1, 4, 2, 5, 6, 0, 99");

        //first part day2
        List<Integer> intcode = IntComputer.getIntcode(path);
        int firstPart = getOutputWithVariables(intcode, 12, 2);
        System.out.println("First part result: " + firstPart);

        //second part day2
        int find = 19690720;
        int noun = 0;
        int verb = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                intcode = IntComputer.getIntcode(path);
                int attempt = getOutputWithVariables(intcode, i, j);
                if (attempt == find) {
                    noun = i;
                    verb = j;
                    System.out.printf("Found!\nnoun: %d, verb: %d.\n", noun, verb);
                    break;
                }
            }
        }
        int secondPart = 100*noun+verb;
        System.out.println("Second part result: "+ secondPart);

    }

    private static int getOutputWithVariables(List<Integer> intcode, int noun, int verb) {
        intcode.set(1, noun);
        intcode.set(2, verb);
        IntComputer.processIntcode(intcode);
        return intcode.get(0);
    }

}
