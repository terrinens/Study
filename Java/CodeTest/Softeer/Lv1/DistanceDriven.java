package Softeer.Lv1;

import java.util.Scanner;

/**
 * 주행거리 비교하기
 * <a href="https://softeer.ai/practice/6253"></a>
 */
public class DistanceDriven {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (a != b) {
                // 기초적인 삼항 연산자.
                System.out.println(a > b ? 'A' : 'B');
            } else {
                System.out.println("same");
            }

            // 더 심플하게도 가능하다.
            // System.out.println(a == b ? "same" : (a > b ? 'A' : 'B'));
        }
    }
}
