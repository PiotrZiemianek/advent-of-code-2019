package day10;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AsteroidTest {

    @Test
    void getAngle() {
        Asteroid asteroid = new Asteroid(0,0);
        float angle1 = asteroid.getAngle(new Asteroid(0, 1));
        float angle2 = asteroid.getAngle(new Asteroid(1, 0));

        assertThat(angle1).isEqualTo(180f);
        assertThat(angle2).isEqualTo(90f);
    }
}