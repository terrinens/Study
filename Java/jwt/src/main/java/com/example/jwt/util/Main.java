package com.example.jwt.util;


import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(System.out))
        ) {
            String input = br.readLine();
            while (input != null && !input.isEmpty()) {
                String[] b = input.split(" ");
                int c = Integer.parseInt(b[0]) + Integer.parseInt(b[1]);
                wr.write(String.valueOf(c));
                wr.newLine();
                input = br.readLine();
            }
            wr.flush();
        }
    }
}