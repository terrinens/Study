package Softeer.Lv2.Clear;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 회의실 예약
 * <a href="https://softeer.ai/practice/6266"></a>
 */
public class MeetingRoomReservations {
    /* 정말로 뜯어 고칠곳이 많은 코드다.
     * 어디까지나 참고하는 용도로 사용하자. */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // 기대값 인자가 2개 이므로 나눈다.
            int[] nm = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            /* 매개인수 N으로 각 이름을 받아야한다.
             * 매개인수로 받는객체로 클래스를 생성한다.*/
            List<MeetingRoom> meetingRooms = Stream.generate(sc::next)
                    .limit(nm[0])
                    .map(MeetingRoom::new).sorted(Comparator.comparing(x -> x.roomName))
                    .collect(Collectors.toList());

            /* 각 방의 이름으로 사용 시작, 종료시간이 들어오는것이 한 라인으로 들어오니 나눠준다.
             * name 09 15 같은 방식으로 들어온다.*/
            Supplier<String[]> roomRes = () -> Stream.generate(sc::next)
                    .limit(3)
                    .toArray(String[]::new);

            Stream.generate(roomRes)
                    // m의 인수만큼 들어오기로 약속되어있으므로 그만큼 제한을 걸어준다.
                    .limit(nm[1])
                    // 그리고 앞서 roomRes 객체로 필요한 데이터를 얻었다.
                    .forEach(res -> meetingRooms.stream()
                            // 미리 생성해둔 MeetingRoom 객체들을 순회하며 같은 이름의 요소들만 수정한다.
                            .filter(room -> room.roomName.equals(res[0]))
                            .forEach(room -> {
                                // 각 값은 int 타입이 필요하니 명시적으로 변환시켜준후 meetingTime 객체에 추가해준다.
                                int start = Integer.parseInt(res[1]);
                                int end = Integer.parseInt(res[2]);
                                room.meetingTime.add(new int[]{start, end});
                            })
                    );

            // 최종 출력값이니 나중에보자.
            for (int i = 0; i < meetingRooms.size(); i++) {
                MeetingRoom meetingRoom = meetingRooms.get(i);
                meetingRoom.roomInfo();

                // 마지막 라인은 절대로 ----- 이 출력되면 안되니 넣은 조건 출력이다.
                if (i + 1 != meetingRooms.size()) {
                    System.out.println("-".repeat(5));
                }
            }
        }
    }

    private static class MeetingRoom {
        private final String roomName;

        /* 사실 정렬 할 필요는 없다.
         * 어디까지나 테스트 할때 정확히 보기 위해 정렬을 해주었다.
         * 사실상 성능을 떨구는 방식.
         * */
        private final TreeSet<int[]> meetingTime = new TreeSet<>(Comparator.comparingInt(x -> x[0]));
        private List<Integer> emptyTimes = new ArrayList<>();

        // 생성자.
        public MeetingRoom(String roomName) {
            this.roomName = roomName;
        }

        // 출력하는 부분은 공통적이므로 정의해두었다.
        public void roomInfo() {
            updateEmptyTimes();
            System.out.printf("Room %s:\n", roomName);

            // 모든 시간이 사용 가능할때를 대비하고자 조건이 추가되다 보니 가독성이 떨어져 따로 빼놨다.
            boolean available = (!meetingTime.isEmpty() && emptyTimes.size() > 1)
                    || (meetingTime.isEmpty() && emptyTimes.size() == 10);
            if (available) {
                List<String> availableTime = getAvailableTime();

                System.out.printf("%d available:\n", availableTime.size());

                for (String s : availableTime) {
                    System.out.println(s);
                }

            } else {
                System.out.println("Not available");
            }
        }

        // 비어있는 시간을 알아내기 위한 코드.
        private void updateEmptyTimes() {
            Set<Integer> usageTime = meetingTime.stream().flatMapToInt(x -> IntStream.range(x[0], x[1]))
                    .boxed()
                    .collect(Collectors.toSet());

            emptyTimes = IntStream.rangeClosed(9, 18)
                    .filter(x -> !usageTime.contains(x))
                    .boxed()
                    .collect(Collectors.toList());
        }

        /* getAvailableTime을 실행할때 특정 구간부터 특정 구간까지 기록 해야하는데
         * 그 와중에 정말로 필요한 시간만 얻어냈어야했다.*/
        private List<Integer> getBrakes() {
            List<Integer> brakes = new ArrayList<>(List.of(18));
            for (int i = 0, j = 1; i < emptyTimes.size(); i++, j++) {
                int iter = emptyTimes.get(i);
                int future = (j < emptyTimes.size())
                        ? emptyTimes.get(j)
                        : emptyTimes.get(emptyTimes.size() - 1);

                if (iter + 1 < future) {
                    brakes.add(iter);
                }
            }

            return brakes;
        }

        // 사용 가능한 시간을 얻어내기 위한 코드다.
        private List<String> getAvailableTime() {
            List<Integer> brakes = getBrakes();
            List<String> emptyList = new ArrayList<>();
            int temp = emptyTimes.get(0);

            ListIterator<Integer> listIter = emptyTimes.listIterator();

            while (listIter.hasNext()) {
                int emTime = listIter.next();
                if (brakes.contains(emTime)) {
                    if (emTime == 18 && temp != 18) {
                        emptyList.add(String.format("%02d-%02d", temp, emTime));
                        break;
                    } else {
                        if (listIter.hasNext()) {
                            emptyList.add(String.format("%02d-%02d", temp, emTime + 1));
                            temp = listIter.next();
                            listIter.previous();
                        } else if (temp != 18) {
                            emptyList.add(String.format("%02d-%02d", temp, 18));
                            break;
                        }
                    }
                }
            }

            return emptyList;
        }
    }
}
