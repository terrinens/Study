package Softeer.Lv1.Clear;

import java.io.*;
import java.util.function.Function;

/**
 * Tren del Fin del Mundo
 * <a href="https://softeer.ai/practice/7695"></a>
 */
public class Tren_del_Fin_del_Mundo {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> parse = Integer::parseInt;

        try (br; bw) {
            int n = parse.apply(br.readLine());
            String[] inputs = new String[n];

            for (int i = 0; i < n; i++) inputs[i] = br.readLine();

            int[] ys = new int[n];
            int min = ys[0] = parse.apply(inputs[0].split(" ")[1]);
            int index = 0;

            for (int i = 0; i < n; i++) {
                String xy = inputs[i];
                int z = parse.apply(xy.split(" ")[1]);
                if (min > z) {
                    min = z;
                    index = i;
                }
            }

            bw.write(inputs[index]);
            bw.flush();


        } catch (IOException e) {

        }
    }
}
