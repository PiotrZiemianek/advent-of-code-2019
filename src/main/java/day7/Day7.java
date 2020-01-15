package day7;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static services.IntComputer.*;

public class Day7 {
    private static Path path = Path.of("src/main/resources/day7input.txt");
    private static List<int[]> phaseSettingsList = new ArrayList<>();

    public static void main(String[] args) {
        List<Integer> amplifierControllerSoftware = getIntcode(path);
        int[] phaseSettings = {0, 1, 2, 3, 4};
        permuteArray(phaseSettings, 0, 4);

        Amplifier ampA = new Amplifier(amplifierControllerSoftware);
        Amplifier ampB = new Amplifier(amplifierControllerSoftware);
        Amplifier ampC = new Amplifier(amplifierControllerSoftware);
        Amplifier ampD = new Amplifier(amplifierControllerSoftware);
        Amplifier ampE = new Amplifier(amplifierControllerSoftware);


        List<Integer> toThrustersList = fillToThrusterList(phaseSettingsList, ampA, ampB, ampC, ampD, ampE);


        int maxSignal = toThrustersList.stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);
        System.out.println(maxSignal);

    }

    private static List<Integer> fillToThrusterList(List<int[]> phaseSettingsList,
                                                    Amplifier ampA, Amplifier ampB, Amplifier ampC, Amplifier ampD, Amplifier ampE) {
        List<Integer> toThrustersList = new ArrayList<>();
        for (int[] phaseSettings : phaseSettingsList) {
            ampA.setPhaseSetting(phaseSettings[0]);
            ampA.setInputSignal(0);
            ampB.setPhaseSetting(phaseSettings[1]);
            ampB.setInputSignal(ampA.amplify());
            ampC.setPhaseSetting(phaseSettings[2]);
            ampC.setInputSignal(ampB.amplify());
            ampD.setPhaseSetting(phaseSettings[3]);
            ampD.setInputSignal(ampC.amplify());
            ampE.setPhaseSetting(phaseSettings[4]);
            ampE.setInputSignal(ampD.amplify());
            int signalToThrusters = ampE.amplify();
            toThrustersList.add(signalToThrusters);
        }
        return toThrustersList;
    }

    public static void swap(int[] input, int i, int j) {
        int temp;
        temp = input[i];
        input[i] = input[j];
        input[j] = temp;


    }

    public static void permuteArray(int[] inputArr, int start, int finish) {
        if (start == finish) {
            phaseSettingsList.add(inputArr.clone());

        } else {
            for (int i = start; i <= finish; i++) {
                swap(inputArr, start, i);
                permuteArray(inputArr, start + 1, finish);
                swap(inputArr, start, i);//restoring the original string
            }
        }
    }

}
