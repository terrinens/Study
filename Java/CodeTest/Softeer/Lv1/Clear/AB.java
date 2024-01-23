package Softeer.Lv1.Clear;

import java.util.*;
import java.util.stream.IntStream;

/**
 * A+B문제
 * <a href="https://softeer.ai/practice/6295">...</a>
 */
public class AB {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 주어진 요소 n 에 따라 출력 되어야 하므로 int 인자를 받는다.
            IntStream.range(1, sc.nextInt() + 1)
                    .forEach(x -> {
                        // 그 이후에 받는 인자들 x + y를 명시적으로 계산하고 출력한다.
                        int add = (sc.nextInt() + sc.nextInt());
                        System.out.printf("Case #%d: %d\n", x, add);
                    });
        }
    }
}
