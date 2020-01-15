package day3;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day3 {
    static Path path = Path.of("src/main/resources/day3input.txt");

    public static void main(String[] args) {
        final List<String> input = DataReaderFromFileService.read(path).orElseThrow();
        String[] wire1Path = input.get(0).split(",");
        String[] wire2Path = input.get(1).split(",");

        String[] test1Path = {"R8", "U5", "L5", "D3"};
        String[] test2Path = {"U7", "R6", "D4", "L4"};

        Wire redWire = new Wire(wire1Path);
        Wire blueWire = new Wire(wire2Path);
        List<int[]> redWireAllSections = getWireSections(redWire);
        List<int[]> blueWireAllSections = getWireSections(blueWire);
        List<int[]> crossPoints = new ArrayList<>();
        List<Integer> allWiresLenToCross = new ArrayList<>();

        int redToCross = 0;
        int blueToCross = 0;
        for (int[] redSection : redWireAllSections) {
            blueToCross = 0;

            for (int[] blueSection : blueWireAllSections) {
                boolean isCrossed = isSectionsCross(crossPoints, redSection, blueSection);
                if (isCrossed) {
                    int redLastSectionLenght = getLastSectionLenght(crossPoints, redSection);
                    int wiresLenToCross = redToCross + redLastSectionLenght
                            + blueToCross + getLastSectionLenght(crossPoints, blueSection);
                    allWiresLenToCross.add(wiresLenToCross);
                }
                blueToCross += getSectionLenght(blueSection);
            }
            redToCross += getSectionLenght(redSection);
        }
//        crossPoints.forEach(ints -> {
//            System.out.println(Arrays.toString(ints));
//            System.out.println(ints[0]+ints[1]);
//        });
        int closestCPDist = crossPoints.stream()
                .mapToInt(ints -> Math.abs(ints[0]) + Math.abs(ints[1]))
                .min().orElseGet(() -> {
                    System.out.println("No crosspoints");
                    return 0;
                });
        System.out.println(closestCPDist);
        Collections.sort(allWiresLenToCross);
        int lowestWiresLenToCross = allWiresLenToCross.get(0);
        System.out.println(lowestWiresLenToCross);
    }

    private static int getLastSectionLenght(List<int[]> crossPoints, int[] Section) {
        int[] crossPoint = crossPoints.get(crossPoints.size() - 1);
        int[] redLastSectionToCross = new int[4];
        System.arraycopy(Section, 0, redLastSectionToCross, 0, 2);
        System.arraycopy(crossPoint, 0, redLastSectionToCross, 2, 2);
        return getSectionLenght(redLastSectionToCross);
    }

    private static int getSectionLenght(int[] section) {

        int[] sBorders = new int[2];
        if (isVertivaly(section)) {
            sBorders[0] = section[1];
            sBorders[1] = section[3];
        } else {
            sBorders[0] = section[0];
            sBorders[1] = section[2];
        }
        Arrays.sort(sBorders);
        return sBorders[1] - sBorders[0];
    }

    private static boolean isSectionsCross(List<int[]> crossPoints, int[] redSection, int[] blueSection) {
        boolean isCrossed = false;
        if (isVertivaly(redSection) && !isVertivaly(blueSection)) {
            int[] maybeCrossPoint = {redSection[0], blueSection[1]};
            boolean isBlueContaisPoint = isHorizontalContainsPoint(blueSection, maybeCrossPoint);
            boolean isRedContaisPoint = isVerticalContainsPoint(redSection, maybeCrossPoint);
            isCrossed = isBlueContaisPoint && isRedContaisPoint;
            if (isCrossed) {
                crossPoints.add(maybeCrossPoint);
            }
        } else if ((!isVertivaly(redSection) && isVertivaly(blueSection))) {
            int[] maybeCrossPoint = {blueSection[0], redSection[1]};
            boolean isBlueContaisPoint = isHorizontalContainsPoint(redSection, maybeCrossPoint);
            boolean isRedContaisPoint = isVerticalContainsPoint(blueSection, maybeCrossPoint);
            isCrossed = isBlueContaisPoint && isRedContaisPoint;
            if (isCrossed) {
                crossPoints.add(maybeCrossPoint);
            }
        }
        return isCrossed;
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

    private static List<int[]> getWireSections(Wire wire) {
        List<int[]> wireAllSections = new ArrayList<>();
        for (String move : wire.path) {
            moveToNextTurn(wire, move);
            int[] beforeCoordinate = wire.beforeCoordinate;
            int[] actualCoordinate = wire.actualCoordinate;
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
