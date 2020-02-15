package day7;

import services.AmpIntComputer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class Amplifier {

    private String id;
    private List<Integer> amplifierControllerSoftware;
    AmpIntComputer ampIntComputer;

    public Amplifier(String id, List<Integer> amplifierControllerSoftware) {
        this.id = id;
        this.amplifierControllerSoftware = amplifierControllerSoftware;
        ampIntComputer = new AmpIntComputer();
    }

    public void amplify(ExecutorService ec) {
        Runnable task = () -> {
            Thread.currentThread().setName(id);
            List<Integer> copyOfAmplifierControllerSoftware = new ArrayList<>(amplifierControllerSoftware); //each thread will have software to modify
            ampIntComputer.processIntcode(copyOfAmplifierControllerSoftware);
        };
        ec.execute(task);
    }

    public void setPhaseSetting(int phaseSetting) {
        Buffer.inputSettings(id, phaseSetting);
    }

}
