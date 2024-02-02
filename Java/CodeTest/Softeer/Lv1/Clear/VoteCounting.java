package Softeer.Lv1.Clear;

import java.io.*;

/**
 * 개표
 * <a href="https://softeer.ai/practice/7698"></a>
 */
public class VoteCounting {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        try (br; bw) {
            int t = Integer.parseInt(br.readLine());

            int[] ns = new int[t];
            for (int i = 0; i < t; i++) {
                ns[i] = Integer.parseInt(br.readLine());
            }

            String crose = "++++ ";
            String l = "|";

            for (int n : ns) {
                int ax = (n / 5);
                int az = (n % 5);

                for (int i = 1; i <= ax ; i++) {
                    System.out.print((az != 0 || i != ax) ? crose : crose.trim());
                }

                System.out.print(l.repeat(az));
                System.out.println();
            }


        } catch(IOException e ) {

        }
    }
}
