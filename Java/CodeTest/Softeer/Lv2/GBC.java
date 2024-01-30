package Softeer.Lv2;

import java.io.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * GBC
 * <a hrfe="https://softeer.ai/practice/6270"><a/>
 */
public class GBC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        try (br; bw) {
            String[] part = br.readLine().split(" ");
            /**/
            int N = Integer.parseInt(part[0]);
            int M = Integer.parseInt(part[1]);

            String[] limits = new String[N];
            for (int i = 0; i < N; i++) {
                limits[i] = br.readLine();
            }

            String[] testLine = new String[N];
            for (int i = 0; i < N; i++) {
                testLine[i] = br.readLine();
            }

            int temp = 0;
            for (int i = 0; i < N; i++) {
                String[] limitPart = limits[i].split(" ");
                int lm = Integer.parseInt(limitPart[0]);
                int ls = Integer.parseInt(limitPart[1]);

                String[] testPart = testLine[i].split(" ");
                int tm = Integer.parseInt(testPart[0]);
                int ts = Integer.parseInt(testPart[1]);


            }
        }
    }
}
