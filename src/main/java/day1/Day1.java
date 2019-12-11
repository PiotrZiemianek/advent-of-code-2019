package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

class Day1 {
    public static void main(String[] args) {
        Integer integer = calculateFuelForMass(1969);
        System.out.println(integer);
        System.out.println("------------------------------");
        getSum();
        System.out.println(calcTotalFuelForModule(100756));
    }

    static Integer calculateFuelForMass(Integer mass) {
        int i = (mass / 3) - 2;

        return i;
    }

    static Integer calcTotalFuelForModule(Integer mass) {
        int total = 0;
        while (0 < mass) {
            int fuelForMass = calculateFuelForMass(mass);
            if(fuelForMass>0){
                total += fuelForMass;}
            mass = fuelForMass;
        }
        return total;
    }

    static void getSum() {
        Path path = Path.of("src/main/resources/day1input.txt");
        int sum = 0;
        try (Stream<String> lines = Files.lines(path)) {
            sum = lines.map(Integer::parseInt)
                    .mapToInt(Day1::calcTotalFuelForModule)
                    .sum();
        } catch (IOException e) {
            System.out.println("Nie odnaleziono pliku.");
        }
        System.out.println(sum);
    }
}
