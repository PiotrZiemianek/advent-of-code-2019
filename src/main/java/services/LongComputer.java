package services;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class LongComputer {
    private static Queue<Long> buffer = new LinkedList<>();
    private static int relativeBaseOffset = 0;

    public static void processLongcode(List<Long> longcode) {
        int params = 1;
        int oneParam = 2;
        int twoParams = 3;
        int threeParams = 4;
        for (int i = 0; i < longcode.size(); i += params) {
            int instruction = getFromMemory(longcode, i).intValue();
            int opcode = instruction % 100;
            int firstParamMode = instruction / 100 % 10;
            int secondParamMode = instruction / 1000 % 10;
            int thirdParamMode = instruction / 10000 % 10;
            switch (opcode) {
                case 1:
                    long paramToAdd1 = getFromMemory(longcode, i + 1);
                    long paramToAdd2 = getFromMemory(longcode, i + 2);
                    int addResultIndex = getFromMemory(longcode, i + 3).intValue();
                    Long valueToAdd1 = getParamOnMode(firstParamMode, paramToAdd1, longcode);
                    Long valueToAdd2 = getParamOnMode(secondParamMode, paramToAdd2, longcode);
                    putToMemory(longcode, addResultIndex, valueToAdd1 + valueToAdd2, thirdParamMode);
                    params = threeParams;
                    break;
                case 2:
                    long paramToMulti1 = getFromMemory(longcode, i + 1);
                    long paramToMulti2 = getFromMemory(longcode, i + 2);
                    int multiResultIndex = getFromMemory(longcode, i + 3).intValue();
                    Long valueToMulti1 = getParamOnMode(firstParamMode, paramToMulti1, longcode);
                    Long valueToMulti2 = getParamOnMode(secondParamMode, paramToMulti2, longcode);
                    putToMemory(longcode, multiResultIndex, valueToMulti1 * valueToMulti2, thirdParamMode);
                    params = threeParams;
                    break;
                case 3: // input data to memory(longcode) from buffer
                    int indexToInput = getFromMemory(longcode, i + 1).intValue();
                    putToMemory(longcode, indexToInput, buffer.remove(), firstParamMode);
                    params = oneParam;
                    break;
                case 4: //output data from memory(longcode) to buffer
                    long paramToOutput = getFromMemory(longcode, i + 1);
                    Long outputValue = getParamOnMode(firstParamMode, paramToOutput, longcode);
                    buffer.add(outputValue);
                    params = oneParam;
                    break;
                case 5:
                    long paramIsNonZero = getFromMemory(longcode, i + 1);
                    long paramToJumpINZ = getFromMemory(longcode, i + 2);
                    Long valueIsNonZero = getParamOnMode(firstParamMode, paramIsNonZero, longcode);
                    Long valueToJumpINZ = getParamOnMode(secondParamMode, paramToJumpINZ, longcode);

                    if (valueIsNonZero == 0) {
                        params = twoParams;
                    } else {
                        params = 0;
                        i = valueToJumpINZ.intValue();
                    }
                    break;
                case 6:
                    long paramIsZero = getFromMemory(longcode, i + 1);
                    long paramToJumpIfZ = getFromMemory(longcode, i + 2);
                    Long valueIsZero = getParamOnMode(firstParamMode, paramIsZero, longcode);
                    Long valueToJumpIfZ = getParamOnMode(secondParamMode, paramToJumpIfZ, longcode);

                    if (valueIsZero == 0) {
                        params = 0;
                        i = valueToJumpIfZ.intValue();
                    } else {
                        params = twoParams;
                    }
                    break;
                case 7:
                    long paramToCompare1 = getFromMemory(longcode, i + 1);
                    long paramToCompare2 = getFromMemory(longcode, i + 2);
                    int compareResultIndex = getFromMemory(longcode, i + 3).intValue();
                    Long valueToCompare1 = getParamOnMode(firstParamMode, paramToCompare1, longcode);
                    Long valueToCompare2 = getParamOnMode(secondParamMode, paramToCompare2, longcode);

                    if (valueToCompare1 < valueToCompare2) {
                        putToMemory(longcode, compareResultIndex, 1L, thirdParamMode);
                    } else {
                        putToMemory(longcode, compareResultIndex, 0L, thirdParamMode);
                    }
                    params = threeParams;
                    break;
                case 8:
                    long paramIsEquals1 = getFromMemory(longcode, i + 1);
                    long paramIsEquals2 = getFromMemory(longcode, i + 2);
                    int equalsResultIndex = getFromMemory(longcode, i + 3).intValue();
                    Long valueIsEquals1 = getParamOnMode(firstParamMode, paramIsEquals1, longcode);
                    Long valueIsEquals2 = getParamOnMode(secondParamMode, paramIsEquals2, longcode);

                    if (valueIsEquals1.equals(valueIsEquals2)) {
                        putToMemory(longcode, equalsResultIndex, 1L, thirdParamMode);
                    } else {
                        putToMemory(longcode, equalsResultIndex, 0L, thirdParamMode);
                    }
                    params = threeParams;
                    break;
                case 9:
                    relativeBaseOffset += getParamOnMode(firstParamMode, getFromMemory(longcode, i + 1), longcode);
                    params = oneParam;
                    break;
                case 99:
                    i = longcode.size();
                    break;
                default:
                    System.out.println("Wrong opcode!");
                    i = longcode.size();
            }
        }
        relativeBaseOffset = 0;
    }

    public static List<Long> getLongcode(Path path) {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        final String[] stringIntcode = input.get(0).split(",");
        return Arrays.stream(stringIntcode)
                .map(Long::parseLong)
                .collect(Collectors
                        .toCollection(ArrayList::new));
    }

    public static void setInput(Long input) {
        LongComputer.buffer.add(input);
    }

    public static Long getOutput() {
        return buffer.remove();
    }


    /**
     * Returns value from parameter on selected mode.
     *
     * @param mode      <br> 0 - position mode - parameter is interpreted as a address in memory, <br>
     *                  1 - immediate mode - a parameter is interpreted as a value, <br>
     *                  2 - relative mode - like position mode but address refers to is itself plus the current relative base.<br><br>
     * @param parameter of instucrion<br><br>
     * @param longcode  memory<br>
     * @return value from parameter on selected mode.
     */
    private static Long getParamOnMode(int mode, long parameter, List<Long> longcode) {
        Long param;
        switch (mode) {
            case 0:
                param = getFromMemory(longcode, (int) parameter);
                break;
            case 1:
                param = parameter;
                break;
            case 2:
                param = getFromMemory(longcode, (int) (parameter + relativeBaseOffset));
                break;
            default:
                throw new IllegalStateException("Unexpected mode value: " + mode);
        }
        return param;
    }

    private static Long getFromMemory(List<Long> longcode, int intex) {
        int size = longcode.size() - 1;
        if (intex > size) {
            for (int i = 0; i < intex - size; i++) {
                longcode.add(0L);
            }
        }
        return longcode.get(intex);
    }

    private static void putToMemory(List<Long> longcode, int intex, Long value, int mode) {
        int size = longcode.size() - 1;
        if (mode == 2) {
            intex += relativeBaseOffset;
        }
        if (intex > size) {
            for (int i = 0; i < intex - size; i++) {
                longcode.add(0L);
            }
        }
        longcode.set(intex, value);
    }
}
