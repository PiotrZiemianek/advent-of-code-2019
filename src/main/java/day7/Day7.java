package day7;

import services.IntComputerDay7;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static services.IntComputerDay7.*;

public class Day7 {
    private static Path path = Path.of("src/main/resources/day7input.txt");
    private static List<int[]> phaseSettingsList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        List<Integer> amplifierControllerSoftware = getIntcode(path);
        int[] phaseSettings = {5, 6, 7, 8, 9};
        permuteArray(phaseSettings, 0, 4);

        Amplifier ampA = new Amplifier("1", amplifierControllerSoftware);
        Amplifier ampB = new Amplifier("2", amplifierControllerSoftware);
        Amplifier ampC = new Amplifier("3", amplifierControllerSoftware);
        Amplifier ampD = new Amplifier("4", amplifierControllerSoftware);
        Amplifier ampE = new Amplifier("5", amplifierControllerSoftware);

        List<Integer> toThrustersList = fillToThrusterList(phaseSettingsList, ampA, ampB, ampC, ampD, ampE);


        int maxSignal = toThrustersList.stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);
        System.out.println(maxSignal);

    }

    private static List<Integer> fillToThrusterList(List<int[]> phaseSettingsList,
                                                    Amplifier ampA, Amplifier ampB, Amplifier ampC,
                                                    Amplifier ampD, Amplifier ampE) throws InterruptedException {
        List<Integer> toThrustersList = new ArrayList<>();
        List<Amplifier> ampsList = new ArrayList<>();
        ampsList.add(ampA);
        ampsList.add(ampB);
        ampsList.add(ampC);
        ampsList.add(ampD);
        ampsList.add(ampE);

        for (int[] phaseSettings : phaseSettingsList) {

            ExecutorService ec = Executors.newFixedThreadPool(5);
            ampA.setInputSignal(0);

            int phaseSettingIndex = 0;
            for (Amplifier amp : ampsList) {
                amp.setPhaseSetting(phaseSettings[phaseSettingIndex]);
                amp.amplify(ec);
                phaseSettingIndex++;
            }

            ec.shutdown();
            ec.awaitTermination(10, TimeUnit.SECONDS);


            int signalToThrusters = IntComputerDay7.getAmplifyOutput();
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
                swap(inputArr, start, i);//restoring the original int
            }
        }
    }

}