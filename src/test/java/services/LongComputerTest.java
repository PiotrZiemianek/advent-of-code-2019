package services;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LongComputerTest {

    @Test
    void processLongcode() {
        List<Long> longcode = LongComputer.getLongcode( Path.of("src/main/resources/day9input_copytest.txt"));
        System.out.println(longcode);
        LongComputer.processLongcode(longcode);
        for (int i = 0; i < 16; i++) { //todo test
//            assertEquals();
        }
    }
}