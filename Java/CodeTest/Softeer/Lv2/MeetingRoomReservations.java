package Softeer.Lv2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 회의실 예약
 * <a href="https://softeer.ai/practice/6266"></a>
 */
public class MeetingRoomReservations {
    /*
     * 3 7 회의실 수, 예약된 회의 수
     * n m
     * n개 회의실 이름, m
     * sonata 14 16 회의실 이름, 시간
     * */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int[] nm = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            List<MeetingRoom> meetingRooms = Stream.generate(sc::next)
                    .limit(nm[0])
                    .map(MeetingRoom::new)
                    .toList();

            Supplier<String[]> roomRes = () -> Stream.generate(sc::next)
                    .limit(3)
                    .toArray(String[]::new);

            Stream.generate(roomRes)
                    .limit(nm[1])
                    .forEach(res -> meetingRooms.stream()
                            .filter(room -> room.roomName.equals(res[0]))
                            .forEach(room -> {
                                int start = Integer.parseInt(res[1]);
                                int end = Integer.parseInt(res[2]);
                                room.meetingTime.add(new int[]{start, end});
                            }));

            meetingRooms.forEach(MeetingRoom::roomInfo);
        }
    }

    private static class MeetingRoom {
        private final String roomName;
        private final TreeSet<int[]> meetingTime = new TreeSet<>(Comparator.comparingInt(x -> x[0]));
        private final List<Integer> emptyTimes = new ArrayList<>(
                IntStream.rangeClosed(9, 18)
                        .boxed()
                        .toList()
        );

        public MeetingRoom(String roomName) {
            this.roomName = roomName;
        }

        public void roomInfo() {
            updateEmptyTimes();
            System.out.println(emptyTimes);
            System.out.printf("Room %s:\n", roomName);

            if (!meetingTime.isEmpty() && emptyTimes.size() > 1) {
                List<String> availableTime = getAvailableTime();

                System.out.printf("%d available:\n", availableTime.size());

                for (String s : availableTime) {
                    System.out.println(s);
                }

            } else {
                System.out.println("Not available");
            }
            System.out.println("-----");
        }

        private void updateEmptyTimes() {
            Iterator<int[]> using = meetingTime.iterator();
            Iterator<int[]> future = meetingTime.iterator();
            future.next();

            /* 시작 시간은 무조건 삭제 되어야 한다.
             * 시작 시간과 끝나는 시간의 간격은 무조건 1시간 이상이므로
             * 시작 시간은 절대 다음에 사용될 수 없다.
             * */
            while (using.hasNext()) {
                int[] seTime = using.next();
                int delSt = seTime[0];
                int delEt = seTime[1] - 1;

                emptyTimes.removeAll(IntStream.rangeClosed(delSt, delEt).boxed().toList());
            }
        }

        private List<String> getAvailableTime() {
            List<String> available = new ArrayList<>();

            int start = emptyTimes.get(0);
            int end = emptyTimes.get(0);

            Iterator<Integer> emIt = emptyTimes.iterator();

            for (int i = 0; emIt.hasNext(); i++) {
                int emTime = emIt.next();
                int fuTime = (i + 1 < emptyTimes.size())
                        ? emptyTimes.get(i + 1)
                        : emptyTimes.get(emptyTimes.size() - 1);

                if (emIt.hasNext() && emTime != (fuTime - 1)) {
                    int interval = fuTime - emTime;
                    available.add(String.format("%02d-%02d", start, end + interval));
                    start = fuTime;
                    end = fuTime + 1;
                }
            }

            return available;
        }
    }


    /*
    * for (int i = 0; it.hasNext(); i++) {
                it.next();
                int value = emptyTimes.get(i);

                if (!it.hasNext()) {
                    int[] empty = new int[]{temp - 1, value};
                    available.add(emptyTime.apply(empty));
                    break;
                }

                int nextValue = emptyTimes.get(i + 1);
                if ((value + 1) != nextValue) {
                    int x = (value == 9)
                            ? (temp - 1)
                            : temp;

                    int[] empty = new int[]{x, value};
                    available.add(emptyTime.apply(empty));
                    temp = nextValue;
                }
            }
    * */
}
