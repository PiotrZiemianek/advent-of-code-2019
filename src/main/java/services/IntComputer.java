package services;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntComputer {
    public static void processIntcode(List<Integer> intcode) {
        int params = 1;
        for (int i = 0; i < intcode.size(); i += params) {
            int oneParam = 2;
            int threeParam = 4;
            Integer opcode = intcode.get(i);
            switch (opcode) {
                case 1:
                    int indexToAdd1 = intcode.get(i + 1);
                    int indexToAdd2 = intcode.get(i + 2);
                    int addResultIndex = intcode.get(i + 3);
                    intcode.set(addResultIndex, intcode.get(indexToAdd1) + intcode.get(indexToAdd2));
                    params = threeParam;
                    break;
                case 2:
                    int indexToMulti1 = intcode.get(i + 1);
                    int indexToMulti2 = intcode.get(i + 2);
                    int multiResultIndex = intcode.get(i + 3);
                    intcode.set(multiResultIndex, intcode.get(indexToMulti1) * intcode.get(indexToMulti2));
                    params = threeParam;
                    break;
                case 3:
                    int indexToInput = intcode.get(i + 1);
                    params = oneParam;
                    break;
                case 4:
                    int indexToOutput = intcode.get(i + 1);
                    int output = intcode.get(indexToOutput);
                    params = oneParam;
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

    public static List<Integer> getIntcode(Path path) {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        final String[] stringIntcode = input.get(0).split(",");
        return Arrays.stream(stringIntcode)
                .map(Integer::parseInt)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }
}
