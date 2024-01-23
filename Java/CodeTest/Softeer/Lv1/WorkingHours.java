package Softeer.Lv1;

import java.util.regex.Pattern;
import java.util.*;

/**
 * 근무 시간
 * <a href="https://softeer.ai/practice/6254"></a>
 */
public class WorkingHours {
    /**
     * 더 쉬운 이해를 위한 코드 분리.
     * 넘어오는 시간의 형태 hh:mm 을 숫자로 인식 시켜 배열형태로 변환시킨다.
     */
    private static int[] slice(String time) {
        return Arrays.stream(time.split(":"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 확실성을 위한 패턴 정의. \\d 에 대한것은 문서를 참조할것.
            Pattern pattern = Pattern.compile("\\d\\d:\\d\\d");

            int allTime = 0;

            for (int i = 0; i < 5; i++) {

                // slice 메서드로 인해 [0]은 시간 [1]은 분을 가지고 있다.
                int[] start = slice(sc.next(pattern));
                int[] end = slice(sc.next(pattern));

                allTime += (end[0] - start[0]) * 60;
                allTime += (end[1] - start[1]);
            }

            System.out.println(allTime);
        }
    }
}
