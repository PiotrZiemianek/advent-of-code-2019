package day3;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day3 {
    static Path path = Path.of("src/main/resources/day3input.txt");

    public static void main(String[] args) {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        String[] wire1 = input.get(0).split(",");
        String[] wire2 = input.get(1).split(",");
        System.out.println(wire1.length);
        System.out.println(wire2.length);

        String[] test1Path = {"R8", "U5", "L5", "D3"};
        String[] test2Path = {"U7", "R6", "D4", "L4"};

        Wire first = new Wire(test1Path);
        Wire second = new Wire(test2Path);
        for (String s : test1Path) {
            System.out.println(s);
            moveToNextTurn(first,s);
            System.out.println(Arrays.toString(first.beforeCoordinate));
            System.out.println(Arrays.toString(first.actualCoordinate));
            System.out.println("----------------------------");
        }

    }

    private static void moveToNextTurn(Wire wire, String move) {

        String direction = String.valueOf(move.charAt(0));
        final String up = "U";
        final String down = "D";
        final String right = "R";
        final String left = "L";
        int x = wire.actualCoordinate[0];
        int y = wire.actualCoordinate[1];
        wire.beforeCoordinate[0] = x;
        wire.beforeCoordinate[1] = y;

        int moveValue = Integer.parseInt(move.replace(direction, ""));

        switch (direction) {
            case up:
                y += moveValue;
                break;
            case down:
                y -= moveValue;
                break;
            case right:
                x += moveValue;
                break;
            case left:
                x -= moveValue;
                break;
            default:
                System.out.println("Wrong direction parameter.");
                break;
        }
        wire.actualCoordinate[0] = x;
        wire.actualCoordinate[1] = y;
    }
}
