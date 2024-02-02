package Softeer.Lv1.Clear;

import java.io.*;
import java.util.Arrays;
import java.util.function.Function;

/**
 * 나무심기
 * <a href="https://softeer.ai/practice/7353"></a>
 */
public class PlantingTrees {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> parse = (str) -> Integer.parseInt(str);

        try (br; bw) {
            int n = parse.apply(br.readLine());
            int[] f;
            f = Arrays.stream(br.readLine().split(" "))
                    .mapToInt(parse::apply)
                    .toArray();

            Arrays.sort(f);
            int max = Math.max(f[0] * f[1], f[n - 2] * f[n - 1]);
            System.out.print(max);

        } catch (IOException e) {

        }
    }
}
