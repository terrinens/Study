package Softeer.Lv2;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 8단 변속기 문제
 * <a href="https://softeer.ai/practice/6283"></a>
 */
public class Transmission_8 {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            // 들어오는 숫자값들의 규칙성들은 " " 으로 정의 되어 있으므로 분리시킨다.
            int[] gears = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            /*
             * IntStream API로 손쉽게 판별한다.
             * allMatch는 하나라도 맞지 않을 경우에 false를 반환 하므로 모든 요소를 평가하지 않는다.
             * 가시성을 위한 IntStream 공통적인 부분을 분리했다.
             *
             * 처음 풀때는 아래와 같이 조건문들에 추가해주었다.
             * IntStream.range(0, gears.length - 1).allMatch(i -> gears[i] < gears[i + 1])
             * IntStream.range(0, gears.length - 1).allMatch(i -> gears[i] > gears[i + 1])
             *
             * 하지만 람다식을 정의하고 재활용 하는것처럼 사용하는 방법이 다소 존재한다.
             * */
            Supplier<IntStream> stream = () -> IntStream.range(0, gears.length - 1);

            if (stream.get().allMatch(i -> gears[i] < gears[i + 1])) {
                System.out.println("ascending");
            } else if (stream.get().allMatch(i -> gears[i] > gears[i + 1])) {
                System.out.println("descending");
            } else {
                System.out.println("mixed");
            }
        }
    }

    /** 사실 더 손쉬운 방식이 존재한다. */
    public static void Solution2(int[] gears) {
        // 성능상으로는 둘중 하나의 평가식을 else 평가식에 넣는것이 좋지만,
        // 가독성을 위해 메서드가 호출되는 순간에 둘다 생성해준다.
        int[] asc = IntStream.rangeClosed(1, 8).sorted().toArray();
        int[] desc = IntStream.iterate(8, i -> i - 1).limit(8).toArray();

        if (Arrays.equals(gears, asc)) {
            System.out.println("ascending");
        } else {
            boolean d = Arrays.equals(gears, desc);
            System.out.println(d ? "descending" : "mixed");
        }
    }
}
