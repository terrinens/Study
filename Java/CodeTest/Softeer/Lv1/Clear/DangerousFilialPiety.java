package Softeer.Lv1.Clear;

import java.io.*;
import java.util.function.Function;

/**
 * 위험한 효도
 * <a href="https://softeer.ai/practice/7368"></a>
 */
public class DangerousFilialPiety {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        try (br; bw) {
            String[] input = br.readLine().split(" ");
            // d는 거리
            // a 초간 뒤 b 초간 앞 반복 술래 터치 후 ba 순으로
            Function<String, Integer> parse = (str) -> Integer.parseInt(str);
            int a = parse.apply(input[0]);
            int b = parse.apply(input[1]);
            int d = parse.apply(input[2]);

            int result = 0;

            int goMin = (int) d / a;
            for (int i = 0; i <= goMin; i++) {
                if (a - d == 0) {
                    result += a;
                    break;
                }

                if (i < goMin) {
                    result += a;
                    result += b;
                } else {
                    result += d - (a * goMin);
                }
            }


            int backMin = (int) d / b;
            for (int i = 0; i <= backMin; i++) {
                if (i < backMin) {
                    result += b;
                    result += a;
                } else {
                    result += d - (b * backMin);
                }
            }

            System.out.print(result);
        } catch (IOException e) {

        }
    }
}
