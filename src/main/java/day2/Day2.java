package day2;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {
    static Path path = Path.of("src/main/resources/day2input.txt");

    public static void main(String[] args) {
        //test
        List<Integer> testIntcode = Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99);
        processIntcode(testIntcode);
        System.out.println(testIntcode);
        System.out.println("30, 1, 1, 4, 2, 5, 6, 0, 99");

        //first part day2
        List<Integer> intcode = getIntcode();
        int firstPart = getOutputWithVariables(intcode, 12, 2);
        System.out.println("First part result: " + firstPart);

        //second part day2
        int find = 19690720;
        int noun = 0;
        int verb = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                intcode = getIntcode();
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
        processIntcode(intcode);
        return intcode.get(0);
    }

    private static void processIntcode(List<Integer> intcode) {
        for (int i = 0; i < intcode.size(); i += 4) {
            switch (intcode.get(i)) {
                case 1:
                    int indexToAdd1 = intcode.get(i + 1);
                    int indexToAdd2 = intcode.get(i + 2);
                    int addResultIndex = intcode.get(i + 3);
                    intcode.set(addResultIndex, intcode.get(indexToAdd1) + intcode.get(indexToAdd2));
                    break;
                case 2:
                    int indexToMulti1 = intcode.get(i + 1);
                    int indexToMulti2 = intcode.get(i + 2);
                    int multiResultIndex = intcode.get(i + 3);
                    intcode.set(multiResultIndex, intcode.get(indexToMulti1) * intcode.get(indexToMulti2));
                    break;
                case 99:
                    i = intcode.size();
                    break;
                default:
                    System.out.println("Wrong opcode!");
                    i = intcode.size();
            }
        }
    }

    private static List<Integer> getIntcode() {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        final String[] stringIntcode = input.get(0).split(",");
        return Arrays.stream(stringIntcode)
                .map(Integer::parseInt)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }
}
