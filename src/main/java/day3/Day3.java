package day3;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.ArrayList;
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

        Wire redWire = new Wire(wire1);
        Wire blueWire = new Wire(wire2);
        List<int[]> redWireAllSections = getWireSections(redWire);
        List<int[]> blueWireAllSections = getWireSections(blueWire);
        List<int[]> crossPoints = new ArrayList<>();

        for (int[] redSection : redWireAllSections) {
            for (int[] blueSection : blueWireAllSections) {
                isSectionCross(crossPoints, redSection, blueSection);
            }
        }
//        crossPoints.forEach(ints -> {
//            System.out.println(Arrays.toString(ints));
//            System.out.println(ints[0]+ints[1]);
//        });
        int cosestCPDist = crossPoints.stream()
                .mapToInt(ints -> Math.abs(ints[0]) + Math.abs(ints[1]))
                .min().orElseGet(() -> {
                    System.out.println("No crosspoints");
                    return 0;
                });
        System.out.println(cosestCPDist);
    }

    private static void isSectionCross(List<int[]> crossPoints, int[] redSection, int[] blueSection) {
        if (isVertivaly(redSection) && !isVertivaly(blueSection)) {
            int[] maybeCrossPoint = {redSection[0], blueSection[1]};
            boolean isBlueContaisPoint = isHorizontalContainsPoint(blueSection, maybeCrossPoint);
            boolean isRedContaisPoint = isVerticalContainsPoint(redSection, maybeCrossPoint);
            boolean isCrossed = isBlueContaisPoint && isRedContaisPoint;
            if (isCrossed) {
                crossPoints.add(maybeCrossPoint);
            }
        } else if ((!isVertivaly(redSection) && isVertivaly(blueSection))) {
            int[] maybeCrossPoint = {blueSection[0], redSection[1]};
            boolean isBlueContaisPoint = isHorizontalContainsPoint(redSection, maybeCrossPoint);
            boolean isRedContaisPoint = isVerticalContainsPoint(blueSection, maybeCrossPoint);
            boolean isCrossed = isBlueContaisPoint && isRedContaisPoint;
            if (isCrossed) {
                crossPoints.add(maybeCrossPoint);
            }
        }
    }

    private static boolean isHorizontalContainsPoint(int[] horizontalSection, int[] maybeCrossPoint) {
        boolean isHorizontalContaisCrossPoint = false;
        if (horizontalSection[0] > horizontalSection[2]) {
            isHorizontalContaisCrossPoint =
                    maybeCrossPoint[0] < horizontalSection[0] && maybeCrossPoint[0] > horizontalSection[2];
        } else if (horizontalSection[0] < horizontalSection[2]) {
            isHorizontalContaisCrossPoint =
                    maybeCrossPoint[0] > horizontalSection[0] && maybeCrossPoint[0] < horizontalSection[2];
        }
        return isHorizontalContaisCrossPoint;
    }

    private static boolean isVerticalContainsPoint(int[] verticalSection, int[] maybeCrossPoint) {
        boolean isVerticaContaisCrossPoint = false;
        if (verticalSection[1] > verticalSection[3]) {
            isVerticaContaisCrossPoint =
                    maybeCrossPoint[1] < verticalSection[1] && maybeCrossPoint[1] > verticalSection[3];
        } else if (verticalSection[1] < verticalSection[3]) {
            isVerticaContaisCrossPoint =
                    maybeCrossPoint[1] > verticalSection[1] && maybeCrossPoint[1] < verticalSection[3];
        }
        return isVerticaContaisCrossPoint;
    }

    private static boolean isVertivaly(int[] section) {
        return section[0] == section[2];
    }

    private static List<int[]> getWireSections(Wire redWire) {
        List<int[]> wireAllSections = new ArrayList<>();
        for (String s : redWire.path) {
            moveToNextTurn(redWire, s);
            int[] beforeCoordinate = redWire.beforeCoordinate;
            int[] actualCoordinate = redWire.actualCoordinate;
            int[] section = new int[4];
            System.arraycopy(beforeCoordinate, 0, section, 0, 2);
            System.arraycopy(actualCoordinate, 0, section, 2, 2);
            wireAllSections.add(section);
        }
        return wireAllSections;
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
