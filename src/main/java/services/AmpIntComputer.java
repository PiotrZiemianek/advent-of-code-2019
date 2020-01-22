package services;

import day7.Buffer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class AmpIntComputer {
    private final Queue<Integer> buffer = new LinkedList<>();
    private final Map<String, Queue<Integer>> threadsBuffer = new ConcurrentHashMap<>();


    public void processIntcode(List<Integer> intcode) {

        int params = 1;
        final int oneParam = 2;
        final int twoParams = 3;
        final int threeParams = 4;
        for (int i = 0; i < intcode.size(); i += params) {
            int opcode = intcode.get(i) % 100;
            int firstParamMode = intcode.get(i) / 100 % 10;
            int secondParamMode = intcode.get(i) / 1000 % 10;
            switch (opcode) {
                case 1:
                    int indexToAdd1 = intcode.get(i + 1);
                    int indexToAdd2 = intcode.get(i + 2);
                    int addResultIndex = intcode.get(i + 3);
                    Integer valueToAdd1 = indexToAdd1;
                    Integer valueToAdd2 = indexToAdd2;
                    if (firstParamMode == 0) {
                        valueToAdd1 = intcode.get(indexToAdd1);
                    }
                    if (secondParamMode == 0) {
                        valueToAdd2 = intcode.get(indexToAdd2);
                    }
                    intcode.set(addResultIndex, valueToAdd1 + valueToAdd2);
                    params = threeParams;
                    break;
                case 2:
                    int indexToMulti1 = intcode.get(i + 1);
                    int indexToMulti2 = intcode.get(i + 2);
                    int multiResultIndex = intcode.get(i + 3);
                    Integer valueToMulti1 = indexToMulti1;
                    Integer valueToMulti2 = indexToMulti2;
                    if (firstParamMode == 0) {
                        valueToMulti1 = intcode.get(indexToMulti1);
                    }
                    if (secondParamMode == 0) {
                        valueToMulti2 = intcode.get(indexToMulti2);
                    }
                    intcode.set(multiResultIndex, valueToMulti1 * valueToMulti2);
                    params = threeParams;
                    break;
                case 3:

                    int indexToInput = intcode.get(i + 1);


                    intcode.set(indexToInput, Buffer.takeFromBuffer());


                    params = oneParam;

                    break;
                case 4:

                    int indexToOutput = intcode.get(i + 1);
                    int output;
                    if (firstParamMode == 0) {
                        output = (intcode.get(indexToOutput));
                    } else {
                        output = (indexToOutput);
                    }

                    Buffer.addToBuffer(output);
                    params = oneParam;

                    break;
                case 5:
                    int indexIsNonZero = intcode.get(i + 1);
                    int indexToJumpINZ = intcode.get(i + 2);
                    int valueIsNonZero = indexIsNonZero;
                    int valueToJumpINZ = indexToJumpINZ;

                    if (firstParamMode == 0) {
                        valueIsNonZero = intcode.get(indexIsNonZero);
                    }
                    if (secondParamMode == 0) {
                        valueToJumpINZ = intcode.get(indexToJumpINZ);
                    }

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
                    int valueIsZero = indexIsZero;
                    int valueToJumpIfZ = indexToJumpIfZ;

                    if (firstParamMode == 0) {
                        valueIsZero = intcode.get(indexIsZero);
                    }
                    if (secondParamMode == 0) {
                        valueToJumpIfZ = intcode.get(indexToJumpIfZ);
                    }
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
                    Integer valueToCompare1 = indexToCompare1;
                    Integer valueToCompare2 = indexToCompare2;
                    if (firstParamMode == 0) {
                        valueToCompare1 = intcode.get(indexToCompare1);
                    }
                    if (secondParamMode == 0) {
                        valueToCompare2 = intcode.get(indexToCompare2);
                    }
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
                    Integer valueIsEquals1 = indexIsEquals1;
                    Integer valueIsEquals2 = indexIsEquals2;
                    if (firstParamMode == 0) {
                        valueIsEquals1 = intcode.get(indexIsEquals1);
                    }
                    if (secondParamMode == 0) {
                        valueIsEquals2 = intcode.get(indexIsEquals2);
                    }
                    if (valueIsEquals1.equals(valueIsEquals2)) {
                        intcode.set(equalsResultIndex, 1);
                    } else {
                        intcode.set(equalsResultIndex, 0);
                    }
                    params = threeParams;
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

}