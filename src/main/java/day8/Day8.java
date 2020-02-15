package day8;

import services.DataReaderFromFileService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8 {
    static Path path = Path.of("src/main/resources/day8input.txt");

    public static void main(String[] args) {
        final String input = DataReaderFromFileService.read(path).orElseThrow().get(0);
        List<char[][]> layers = getLayers(25, 6, input);

        int fewest0Digits = -1;
        int indexFewest0layer = -1;
        for (int i = 0; i < layers.size(); i++) {
            char[][] layer = layers.get(i);
            int counter = countNumberOfDigit('0', layer);
            if (counter < fewest0Digits || fewest0Digits == -1) {
                fewest0Digits = counter;
                indexFewest0layer = i;
            }
        }
        int numberOfDigit1 = countNumberOfDigit('1', layers.get(indexFewest0layer));
        int numberOfDigit2 = countNumberOfDigit('2', layers.get(indexFewest0layer));
        System.out.println(numberOfDigit1 * numberOfDigit2);

        System.out.println("-----------------------");
        char[][] spaceImage = new char[6][25];
        for (int y = 0; y < spaceImage.length; y++) {
            for (int x = 0; x < spaceImage[y].length; x++) {
                for (char[][] layer : layers) {
                    char pixel = layer[y][x];
                    if (pixel == '0') {
                        spaceImage[y][x] = ' ';
                        break;
                    } else if (pixel == '1') {
                        spaceImage[y][x] = 'X';
                        break;
                    }
                }
            }
        }
        printSpaceImage(spaceImage);
    }

    public static List<char[][]> getLayers(int width, int height, String spaceImageFormatPicture) {
        List<char[][]> layers = new ArrayList<>();

        int pointer = 0;
        while (pointer < spaceImageFormatPicture.length()) {
            char[][] layer = new char[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    layer[y][x] = spaceImageFormatPicture.charAt(pointer);
                    pointer++;
                }
            }
            layers.add(layer);
        }

        return layers;
    }

    public static int countNumberOfDigit(char digit, char[][] layer) {
        int counter = 0;
        for (int y = 0; y < layer.length; y++) {

            for (int x = 0; x < layer[y].length; x++) {
                if (layer[y][x] == digit) counter++;
            }
        }
        return counter;
    }

    public static void printSpaceImage(char[][] image) {
        for (char[] digitsOnXAxis : image) {
            for (char digit : digitsOnXAxis) {
                System.out.print(digit+ " ");
            }
            System.out.println(); //next line on Y axis
        }
    }
}
