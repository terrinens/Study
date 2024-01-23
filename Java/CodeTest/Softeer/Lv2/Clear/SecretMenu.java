package Softeer.Lv2.Clear;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecretMenu {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 각 요소 m, n, k가 들어올것을 기대하고 split으로 분할하고 배열형태로 변환한다.
            int[] mnk = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            // 반복적으로 사용될 IntStream을 만든다.
            // l은 몇번 반복될것지 정한다.
            // String API의 contains로 간단히 구분 할 수 있도록 모든 요소를 붙인다.
            Function<Integer, String> scStream = (l) -> IntStream.range(0, l)
                    .mapToObj(x -> sc.next())
                    .collect(Collectors.joining(""));

            String sNumbers = scStream.apply(mnk[0]);
            String pNumbers = scStream.apply(mnk[1]);

            if (pNumbers.contains(sNumbers)) {
                System.out.println("secret");
            } else {
                System.out.println("normal");
            }
        }
    }
}
