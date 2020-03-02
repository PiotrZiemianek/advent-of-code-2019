package day10;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.*;

public class Day10 {
    static Path path = Path.of("src/main/resources/day10input.txt");
    static List<String> input = DataReaderFromFileService.read(path).orElse(Collections.emptyList());

    public static void main(String[] args) {
        input.forEach(System.out::println);
        List<Asteroid> asteroids = getAsteroidsList(input);
        Map<Asteroid, Integer> asteroidsWithNumOfDetected = new HashMap<>();
        for (Asteroid mainAsteroid : asteroids) {
            Set<Float> detectedAngles = new HashSet<>();
            for (Asteroid asteroidToDetect : asteroids) {
                if (!mainAsteroid.equals(asteroidToDetect)) {
                    float angle = mainAsteroid.getAngle(asteroidToDetect);
                    detectedAngles.add(angle);
                }
            }

            asteroidsWithNumOfDetected.put(mainAsteroid, detectedAngles.size());
        }
        Asteroid bestLocalisationAst = Collections
                .max(asteroidsWithNumOfDetected.entrySet(), Map.Entry.comparingByValue())
                .getKey();
        Integer highestDetected = asteroidsWithNumOfDetected.get(bestLocalisationAst);
        System.out.println(bestLocalisationAst + " " + highestDetected);


    }

    private static List<Asteroid> getAsteroidsList(List<String> mapImage) {
        List<Asteroid> asteroids = new ArrayList<>();
        for (int y = 0; y < mapImage.size(); y++) {
            String s = mapImage.get(y);
            for (int x = 0; x < s.length(); x++) {
                char c = s.charAt(x);
                if (c == '#') {
                    asteroids.add(new Asteroid(x, y));
                }
            }
        }
        return asteroids;
    }

}

