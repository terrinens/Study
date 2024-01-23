package Softeer.Lv2.Clear;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 비밀 메뉴
 * <a href="https://softeer.ai/practice/6269"></a>
 * 이 문제에 의문점이 존재한다. 첫번째 라인에서 입력 받는것을 M, N, K로 규정하고 있는데
 * K요소는 각 정수의 이하값을 나타내기 위해 사용된다.
 * 하지만 K요소로 제한을 두지 않아도 테스트가 통과된다.
 * 그러므로 K요소를 사용하지 않았다.
 */
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
