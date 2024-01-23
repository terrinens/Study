package Softeer.Lv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 지도 자동 구축
 * <a href="https://softeer.ai/practice/6280"></a>
 */
public class MAC {
    public static void main(String[] args) {
    }

    /**
     * 실패의 이유?
     * 사각형 하나에서 점점 제곱해서 늘어나는 형태의 마주치는 변xy를 없애는 문제인줄 알았지만
     * 사각형 하나의 중심에서 부터 + 형태의 선이 생기는것. 생각을 잘못함.
     */
    void fall1() {
        try (Scanner sc = new Scanner(System.in)) {
            int dot = 4;
            int iter = sc.nextInt();

            for (int i = 0; i < iter; i++) {
                dot = dot * 2;
            }

            int xy = Math.round((float) dot / 3);
            System.out.println(xy * xy);
        }
    }
}