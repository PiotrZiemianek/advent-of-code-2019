package services;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class LongIntComputer {
    private static Queue<Integer> buffer = new LinkedList<>();

    private static int relativeBaseOffset = 0;

    public static void processIntcode(List<Integer> intcode) {
        int params = 1;
        int oneParam = 2;
        int twoParams = 3;
        int threeParams = 4;
        for (int i = 0; i < intcode.size(); i += params) {
            int opcode = intcode.get(i) % 100;
            int firstParamMode = intcode.get(i) / 100 % 10;
            int secondParamMode = intcode.get(i) / 1000 % 10;
            switch (opcode) {
                case 1:
                    int indexToAdd1 = intcode.get(i + 1);
                    int indexToAdd2 = intcode.get(i + 2);
                    int addResultIndex = intcode.get(i + 3);
                    Integer valueToAdd1 = getParamOnMode(firstParamMode, indexToAdd1, intcode);
                    Integer valueToAdd2 = getParamOnMode(secondParamMode, indexToAdd2, intcode);
                    intcode.set(addResultIndex, valueToAdd1 + valueToAdd2);
                    params = threeParams;
                    break;
                case 2:
                    int indexToMulti1 = intcode.get(i + 1);
                    int indexToMulti2 = intcode.get(i + 2);
                    int multiResultIndex = intcode.get(i + 3);
                    Integer valueToMulti1 = getParamOnMode(firstParamMode, indexToMulti1, intcode);
                    Integer valueToMulti2 = getParamOnMode(secondParamMode, indexToMulti2, intcode);
                    intcode.set(multiResultIndex, valueToMulti1 * valueToMulti2);
                    params = threeParams;
                    break;
                case 3:
                    int indexToInput = intcode.get(i + 1);
                    intcode.set(indexToInput, buffer.remove());
                    params = oneParam;
                    break;
                case 4:
                    int indexToOutput = intcode.get(i + 1);
                    Integer output = getParamOnMode(firstParamMode, indexToOutput, intcode);
                    buffer.add(output);
                    params = oneParam;
                    break;
                case 5:
                    int indexIsNonZero = intcode.get(i + 1);
                    int indexToJumpINZ = intcode.get(i + 2);
                    int valueIsNonZero = getParamOnMode(firstParamMode, indexIsNonZero, intcode);
                    int valueToJumpINZ = getParamOnMode(secondParamMode, indexToJumpINZ, intcode);

                    if (valueIsNonZero == 0) {
                        params = twoParams;
                    } else {
                        params = 0;
                        i = valueToJumpINZ;
                    }
                    break;
                case 6:
                    int indexIsZero = intcode.get(i + 1);
                    int indexToJumpIfZ = intcode.get(i + 2);
                    int valueIsZero = getParamOnMode(firstParamMode,indexIsZero,intcode);
                    int valueToJumpIfZ = getParamOnMode(secondParamMode,indexToJumpIfZ,intcode);

                    if (valueIsZero == 0) {
                        params = 0;
                        i = valueToJumpIfZ;
                    } else {
                        params = twoParams;
                    }
                    break;
                case 7:
                    int indexToCompare1 = intcode.get(i + 1);
                    int indexToCompare2 = intcode.get(i + 2);
                    int compareResultIndex = intcode.get(i + 3);
                    Integer valueToCompare1 = getParamOnMode(firstParamMode,indexToCompare1,intcode);
                    Integer valueToCompare2 = getParamOnMode(secondParamMode,indexToCompare2,intcode);

                    if (valueToCompare1 < valueToCompare2) {
                        intcode.set(compareResultIndex, 1);
                    } else {
                        intcode.set(compareResultIndex, 0);
                    }
                    params = threeParams;
                    break;
                case 8:
                    int indexIsEquals1 = intcode.get(i + 1);
                    int indexIsEquals2 = intcode.get(i + 2);
                    int equalsResultIndex = intcode.get(i + 3);
                    Integer valueIsEquals1 = getParamOnMode(firstParamMode,indexIsEquals1,intcode);
                    Integer valueIsEquals2 = getParamOnMode(secondParamMode,indexIsEquals2,intcode);

                    if (valueIsEquals1.equals(valueIsEquals2)) {
                        intcode.set(equalsResultIndex, 1);
                    } else {
                        intcode.set(equalsResultIndex, 0);
                    }
                    params = threeParams;
                    break;
                case 9:
                    relativeBaseOffset += getParamOnMode(firstParamMode,intcode.get(i + 1),intcode);
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
        relativeBaseOffset = 0;
    }

    public static List<Integer> getIntcode(Path path) {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        final String[] stringIntcode = input.get(0).split(",");
        return Arrays.stream(stringIntcode)
                .map(Integer::parseInt)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }

    public static void setInput(Integer input) {
        LongIntComputer.buffer.add(input);
    }

    public static Integer getOutput() {
        return buffer.remove();
    }


    /**
     * Returns parameter on selected mode.
     *
     * @param mode    <br> 0 - position mode - parameter is interpreted as a address in memory, <br>
     *                1 - immediate mode - a parameter is interpreted as a value, <br>
     *                2 - relative mode - like position mode but address refers to is itself plus the current relative base.<br><br>
     * @param index   address in memory<br><br>
     * @param intcode memory<br>
     * @return parameter on selected mode.
     */
    private static Integer getParamOnMode(int mode, int index, List<Integer> intcode) {
        Integer param;
        switch (mode) {
            case 0:
                param = intcode.get(index);
                break;
            case 1:
                param = index;
                break;
            case 2:
                param = intcode.get(index + relativeBaseOffset);
                break;
            default:
                throw new IllegalStateException("Unexpected mode value: " + mode);
        }
        return param;
    }
}
