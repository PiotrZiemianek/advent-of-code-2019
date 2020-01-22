package day7;

import services.IntComputerDay7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Amplifier {

    private String id;
    private int phaseSetting;
    private Integer inputSignal;
    private List<Integer> amplifierControllerSoftware;

    public Amplifier(String id, List<Integer> amplifierControllerSoftware) {
        this.id = id;
        this.amplifierControllerSoftware = amplifierControllerSoftware;
    }

    public void amplify(ExecutorService ec) {
        Runnable task = () -> {
            Thread.currentThread().setName(id);
            IntComputerDay7.input(phaseSetting);
            if (inputSignal != null) {
                IntComputerDay7.input(inputSignal);
            }
            List<Integer> copyOfAmplifierControllerSoftware = new ArrayList<>(amplifierControllerSoftware); //each thread will have software to modify
            IntComputerDay7.processIntcode(copyOfAmplifierControllerSoftware);
        };
        ec.execute(task);
    }

    public void setPhaseSetting(int phaseSetting) {
        this.phaseSetting = phaseSetting;
    }

    public void setInputSignal(int inputSignal) {
        this.inputSignal = inputSignal;
    }

}
