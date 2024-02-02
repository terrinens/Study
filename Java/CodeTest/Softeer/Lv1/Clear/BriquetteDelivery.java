package Softeer.Lv1.Clear;

import java.io.*;
import java.util.Arrays;

/**
 * 연탄 배달의 시작
 * <a href="https://softeer.ai/practice/7626"></a>
 */
public class BriquetteDelivery {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        try(br; bw) {
            int n = Integer.parseInt(br.readLine()) - 1;
            String[] spl = br.readLine().split(" ");
            int splLen = spl.length;

            int[] array = new int[splLen];
            for (int i = 0; i < splLen; i++) {
                array[i] =  Integer.parseInt(spl[i]);
            }

            int[] dis = new int[n];
            for (int i = 0; i < n; i++) {
                dis[i] = array[i+1] - array[i];
            }

            Arrays.sort(dis);
            int min = dis[0];

            int count = 0;
            for (int di : dis) {
                if (di == min ) {
                    count++;
                } else {
                    break;
                }
            }

            System.out.print(count);
        } catch (IOException e) {}
    }
}
