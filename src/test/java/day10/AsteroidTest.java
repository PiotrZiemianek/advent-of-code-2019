package day10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AsteroidTest {

    @Test
    void getAngle() {
        Asteroid asteroid = new Asteroid(0,0);
        float angle = asteroid.getAngle(new Asteroid(0, 1));
        assertThat(angle).isEqualTo(90.0f);
    }
}