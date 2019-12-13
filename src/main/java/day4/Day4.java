package day4;

import java.util.*;

public class Day4 {

    public static void main(String[] args) {
        int[] range = {168630, 718098};
        List<Integer> maybePasswords = new ArrayList<>();
        for (int password = range[0]; password <= range[1]; password++) {
            int[] digits = Integer.toString(password).chars().map(c -> c - '0').toArray();
            int beforDigit = 0;
            boolean adjacentRule = false;
            boolean neverDecrease = true;
            Map<Integer, Integer> repeated = new HashMap<>();
            for (int digit : digits) {
                if (beforDigit == digit) {
                    if (repeated.containsKey(digit)) {
                        Integer counter = repeated.get(digit);
                        counter++;
                        repeated.put(digit, counter);
                    } else {
                        repeated.put(digit, 1);
                    }
                }
                if (beforDigit > digit) {
                    neverDecrease = false;
                }
                beforDigit = digit;
            }
            adjacentRule = repeated.containsValue(1);
            if (adjacentRule && neverDecrease) {
                maybePasswords.add(password);
            }
        }
        for (Integer maybePassword : maybePasswords) {
            System.out.println(maybePassword);
        }
        System.out.println(maybePasswords.size());
    }
}
