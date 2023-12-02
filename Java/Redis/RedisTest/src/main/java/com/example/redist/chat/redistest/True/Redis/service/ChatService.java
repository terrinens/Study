package com.example.redist.chat.redistest.True.Redis.service;

import com.example.redist.chat.redistest.True.Redis.chatDTO.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRoomMap;

    /**채팅 방을 초기화 하기 위한 메서드*/
    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    /**모든 채팅방 리스트를 반환하기 위한 메서드*/
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    /**특정 방번호를 검색하기 위한 메서드 <br>
     * get으로 String roomId를 받는 이유는 key 값으로 검색하기 위함.  <br>
     * map은 key-vale로 이뤄져 있으니 get으로 가져올때는 key에 부합하는 값을 가져와야함.  <br>
     * 현재 chatRoomMap은 String, ChatRoom으로 이뤄져있으니 String이 key로써 작동한다
     *
     * @param roomId 방 번호를 검색 하기 위한 매개변수*/
    public ChatRoom findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    /**새로운 채팅방을 만들기 위한 서비스 메서드
     * @param newRoomName 채팅방 이름을 지정하기 위한 매개값*/
    public ChatRoom createRoom(String newRoomName) {
        //채팅방 Id가 겹치지 않기 위한 UUID 생성 전략
        String newRoomId = UUID.randomUUID().toString();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(newRoomId)
                .roomName(newRoomName)
                .build();

        /*기존에 채팅 리스트에 새로운 채팅 리스트를 집어넣는다.*/
        chatRoomMap.put(newRoomId, chatRoom);
        return chatRoom;
    }

    /**사용자가 보낸 메세지를 처리하기 위한 메서드
     * @param session 메세지를 보낸 사용자
     * @param message 메세지 내용
     * ObjectMapper를 이용해 Java 객체를 JSON 문자열로 변환한 후, 해당 사용자의 웹소켓 세션을 통해 메세지를 전송.*/
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(
                    new TextMessage(objectMapper.writeValueAsString(message))
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
