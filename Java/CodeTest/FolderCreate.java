import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class FolderCreate {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        try (br; bw) {
            Path root = Path.of(br.readLine());
            String[] input = br.readLine().split(" ");
            int start = Integer.parseInt(input[0]);
            int end = Integer.parseInt(input[1]);

            if (start > end) {
                bw.write("시작 값보다 끝값이 더 큽니다. 비정상적인 입력입니다. 프로그램을 종료합니다.");
                bw.flush();
                br.close();
                bw.close();
            }

            Consumer<Integer> folderCreate = (i) -> {
                try {
                    Path newDir = root.resolve("Lv" + i);
                    Files.createDirectory(newDir);
                    Files.createDirectory(newDir.resolve("Clear"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };

            IntStream.rangeClosed(start, end).forEach(folderCreate::accept);
        }
    }
}
