package Softeer.Lv2;


import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * 전광판
 * <a href="https://softeer.ai/practice/6268"></a>
 */
public class ElectronicDisplayBoard {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int t = sc.nextInt();

            int[] ab = IntStream.range(0, sc.nextInt())
                    .map(sc::nextInt)
                    .toArray();

            /*
            * 7
            * 1 = 2
            * 2 = 5
            * 3 = 5*/
        }
    }
}
