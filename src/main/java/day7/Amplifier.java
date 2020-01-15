package day7;

import java.util.List;

import static services.IntComputer.*;

public class Amplifier {

    private int phaseSetting;
    private int inputSignal;
    private List<Integer> amplifierControllerSoftware;

    public Amplifier(List<Integer> amplifierControllerSoftware) {
        this.amplifierControllerSoftware = amplifierControllerSoftware;
    }

    public int amplify(){
        setInput(phaseSetting);
        setInput(inputSignal);
        processIntcode(amplifierControllerSoftware);
        return getOutput();
    }


    public void setPhaseSetting(int phaseSetting) {
        this.phaseSetting = phaseSetting;
    }

    public void setInputSignal(int inputSignal) {
        this.inputSignal = inputSignal;
    }
}
