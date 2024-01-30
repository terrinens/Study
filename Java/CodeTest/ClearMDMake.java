import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("CallToPrintStackTrace")
public class ClearMDMake {
    public static void main(String[] args) throws IOException {
        Path rootDir = Path.of("D:\\All Conding Space\\Study\\Java\\CodeTest");

        Path[] subDir;
        Path mdPath = rootDir.resolve("Report.MD");
        try (Stream<Path> pathStream = Files.list(rootDir);
             Stream<Path> mdFindStream = Files.list(rootDir)
        ) {
            Path thisFilePath = Paths.get(System.getProperty("user.dir"));
            Path[] ignores = new Path[]{
                    Path.of("D:\\All Conding Space\\Study\\.gitignore"),
                    thisFilePath.resolve("out"),
                    thisFilePath.resolve(".idea")
            };

            subDir = pathStream
                    .filter(Files::isDirectory)
                    .filter(dir -> notIgnorePattern(dir, ignores))
                    .toArray(Path[]::new);

            if (mdFindStream.noneMatch(mdPath::equals)) Files.createFile(mdPath);
        }

        Arrays.stream(subDir).forEach(subRoot -> report(subRoot, mdPath));
    }

    private static void report(Path dir, Path md) {
        String dirName = dir.getFileName().toString();

        try {
            List<String> mdLines = Files.readAllLines(md);

            int[] dirNameHeaderIndex = findHeaderStartEndIndex(mdLines, "##", dirName);
            int headerStart = dirNameHeaderIndex[0];
            int headerEnd = dirNameHeaderIndex[1];

            BiFunction<Integer, Integer, List<String>> contentSlice = (start, end) -> mdLines.stream()
                    .skip(start)
                    .limit(end)
                    .collect(Collectors.toList());

            /*기존 내용을 유지하면서 새 내용만을 바꾸기 위해 3가지로 분리해둡니다.*/
            List<String> topContents = contentSlice.apply(0, headerStart);
            List<String> concentration = new ArrayList<>();
            List<String> downContents = contentSlice.apply(headerEnd, mdLines.size());


            Map<Path, List<Path>> subFolderFileList = clearDirJavaList(dir);
            long numberOfSolutions = subFolderFileList.values().stream()
                    .mapToLong(List::size)
                    .sum();

            Stream<Path> tryFiles = Files.list(dir);
            long numberOfTry = tryFiles.filter(Files::isRegularFile).count();
            tryFiles.close();

            concentration.add(String.format("## %s", dirName));
            concentration.add(String.format("총 풀이 수 %d </br>", numberOfSolutions));
            concentration.add(String.format("시도중인 수 %d </br>", numberOfTry));
            concentration.add(String.format("<details> \n<summary>%s 풀이 목록</summary> \n", dirName));

            for (Path key : subFolderFileList.keySet()) {
                String lvDir = key.getParent().getFileName().toString();
                int clearSize = subFolderFileList.get(key).size();
                concentration.add(String.format("<details> \n<summary>%s Clear : %d</summary> \n", lvDir, clearSize));

                for (Path value : subFolderFileList.get(key)) {
                    /*Clear 폴더 안에 있는 파일들의 리스를 작성하면서 링크를 따오기 위한 코드 블락 입니다.*/
                    String link = anchorTagLinkExtraction(key.resolve(value).toFile());
                    String content = String.format("[%s](%s) \n", value, link);
                    concentration.add(content);
                }
                concentration.add("</details>\r");
            }
            concentration.add("</details>\r");

            List<String> allJoin = Stream.of(topContents, concentration, downContents)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            Files.write(md, allJoin, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("MD 문서 불러들이는중 오류를 발생했습니다.");
        }
    }

    /**
     * 해당 메서드는 'lines' 매개 인수에서 'tag' 값과 'name' 값이 동일한 요소의 인덱스값 및
     * 가장 가까운 'tag' 요소의 인덱스 값을 찾습니다.
     * 하지만... 해당 설계와는 조금 다르게 작동해서 손볼점이 많습니다.
     *
     * @param lines 읽고, 찾아내기 위한 리스트
     * @param tag   md 문서에 특정 태그를 찾기 위한 매개 변수 입니다.
     * @param name  태그 이후에 오는 이름값을 위한 매개 변수 입니다.
     * @return int[] { StartIndex, EndIndex }
     */
    private static int[] findHeaderStartEndIndex(List<String> lines, String tag, String name) {
        String header = tag + " " + name;
        int start = lines.contains(header)
                ? lines.indexOf(header)
                : lines.size();

        int end = IntStream.range(start + 1, lines.size())
                .filter(i -> lines.get(i).startsWith("##"))
                .findFirst()
                .orElse(start == 0 ? start : lines.size());

        return new int[]{start, end};
    }

    /**
     * 해당 메서드는 하위 폴더에 'Clear'폴더의 존재를 확인하고 그 안에 있는 java 파일의 목록을 작성하기 위한 메서드 입니다. </br>
     *
     * @param path 작업을 진행할 디렉토리 경로값
     * @return 'path'의 하위 폴더의 모든 Clear 폴더의 java 파일 리스트
     */
    private static Map<Path, List<Path>> clearDirJavaList(Path path) {

        Map<Path, List<Path>> map = new HashMap<>();
        try (Stream<Path> fileList = Files.list(path)) {
            Path[] subDirs = fileList.filter(Files::isDirectory)
                    .sorted()
                    .toArray(Path[]::new);

            for (Path subDir : subDirs) {
                Path clearDir = subDir.resolve("Clear");
                Stream<Path> subFileList = Files.list(clearDir);

                List<Path> javaFiles = subFileList.filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        /* Path의 endwith 메서드는 경로값의 마지막이 x 와 일치 하는것인지 검사합니다..
                         * 즉 Stirng의 endwith와는 다른 작동 방식을 가지고 있으므로 toString으로 변환하여 검사를 합니다.*/
                        .filter(fileName -> fileName.toString().endsWith(".java"))
                        .collect(Collectors.toList());

                map.put(clearDir, javaFiles);

                subFileList.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 해당 메서드는 현재 폴더가 무시 대상인지 아닌지 확인하기 위한 메서드입니다.
     * dirPath로 넘겨 받은 디렉토리가 ignores에 해당되는 폴더일 경우에 'false'를 반환합니다.
     * 모든 무시 대상에 해당되지 않을 경우에 'true'를 반환합니다.
     *
     * @param dirPath 검사 할 디렉토리의 위치입니다.
     * @param ignores 검사시 사용될 디렉토리의 위치들입니다. </br>
     *                해당 위치가 폴더일 경우에 하위 폴더가 전부 무시 처리 됩니다. </br>
     *                하지만 텍스트 형식 혹은 gitignore 형태를 가지고 있다면 glob 형식으로 변환되어 검사를 실행합니다.
     */
    private static boolean notIgnorePattern(Path dirPath, Path... ignores) {
        boolean result = true;
        for (Path ignorePath : ignores) {
            if (Files.isDirectory(ignorePath) && ignorePath.equals(dirPath)) {
                result = false;
                break;
            } else if (Files.isRegularFile(ignorePath)) {
                try (BufferedReader reader = new BufferedReader(new FileReader(ignorePath.toFile()))) {
                    result = reader.lines()
                            .filter(l -> !l.startsWith("#") && !l.startsWith("!") && !l.isBlank())
                            .map(line -> FileSystems.getDefault().getPathMatcher("glob:" + line))
                            .noneMatch(line -> line.matches(dirPath));
                } catch (IOException e) {
                    throw new RuntimeException(String.format("%s 폴더를 찾지 못했습니다.", ignorePath));
                }
            }
        }

        return result;
    }

    /**
     * 자바파일에서 Javadoc 주석을 읽어들이고 해당 문서에 링크가 존재하면 반환하는 메서드 입니다.
     * 아래와 같은 Javadoc 내용이 존재한다면 제일 첫번째 앵커 태그의 링크를 가져옵니다.
     * <pre>
     *  * 회의실 예약
     *  * <a href="https://softeer.ai/practice/6266"></a>
     * </pre>
     *
     * @param java 자바 파일
     */
    private static String anchorTagLinkExtraction(File java) {
        try (BufferedReader reader = new BufferedReader(new FileReader(java))) {
            String anchorTag = reader.lines()
                    .map(String::strip)
                    .dropWhile(line -> !line.equals("/**"))
                    .takeWhile(line -> !line.equals("*/"))
                    .map(line -> line.replace("*", "").strip())
                    .filter(line -> line.startsWith("<a href"))
                    .collect(Collectors.toList())
                    .get(0);

            int linkStart = anchorTag.indexOf("\"") + 1;
            int linkEnd = anchorTag.lastIndexOf("\"");

            return anchorTag.substring(linkStart, linkEnd);

        } catch (IOException e) {
            throw new RuntimeException(String.format("%s \n파일을 찾지 못했거나 읽어들이지 못했습니다. \n%s", java, e));
        }
    }
}
