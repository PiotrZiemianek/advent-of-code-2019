package day10;

import day4.Asteroid;
import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10 {
    static Path path = Path.of("src/main/resources/day10input.txt");
    static List<String> input = DataReaderFromFileService.read(path).orElse(Collections.emptyList());

    public static void main(String[] args) {
        List<Asteroid> asteroids = new ArrayList<>();
        input.forEach(System.out::println);
        for (int y = 0; y < input.size(); y++){
            String s = input.get(y);
            for (int x = 0; x < s.length() ; x++) {
                char c = s.charAt(x);
                if (c=='#'){
                   asteroids.add(new Asteroid(x,y));
                }
            }
        }
        System.out.println(asteroids.size());
    }
}

