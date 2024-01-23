package Softeer.Lv2;

import java.util.*;


public class Load2 {
    //2레벨 장애물 인식 프로그램
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int count = sc.nextInt();

            boolean[][] road = new boolean[count][count];
            for (int y = 0; y < count; y++) {
                String[] inputs = sc.next().split("");

                for (int x = 0; x < count; x++) {
                    road[y][x] = "1".equals(inputs[x]);
                }
            }

            System.out.println(Arrays.deepToString(road));
        }
    }

    /*
    * 7
1110111
0110101
0110101
0000100
0110000
0111110
0110000
    * */
}
