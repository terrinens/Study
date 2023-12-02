package com.example.redist.chat.redistest.First.Redis.handler;

import com.example.redist.chat.redistest.First.Redis.chatDTO.ChatMessage;
import com.example.redist.chat.redistest.First.Redis.chatDTO.ChatRoom;
import com.example.redist.chat.redistest.First.Redis.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSockChatHandler extends TextWebSocketHandler {
    
    //private final 들은 구현순서 7번 이후에 작성할것
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    //구현 순서 1번에서 사용하는 오버라이드
/*    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        TextMessage textMessage = new TextMessage("어서오세요, 테스트 채팅 입니다");
        session.sendMessage(textMessage);
    }*/

    //구현 순서 7번에서 사용하는 오버라이드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        //이후부터 바뀐 부분
        //오브젝트 맵퍼를 사용해서 값을 읽어오고 DTO에 값을 대입시킨다.
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        //챗 서비스를 사용해 해당 방번호를 찾는다.
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());

        //첫 입장시 ~가 입장하셨습니다 메세지를 발송. 아닐 경우 그냥 입장한다.
        room.handleActions(session, chatMessage, chatService);
    }
}
