package com.example.redist.chat.redistest.First.Redis.chatDTO;

import com.example.redist.chat.redistest.First.Redis.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    /**방 번호*/
    private final String roomId;
    
    /**방 이름*/
    private final String roomName;

    /**유저를 받기 위한 변수 <br>
     * Set으로 지정하는 이유는 동일한 유저를 받지 않기 위함 <br>
     * Set을 단순하게 표한하자면 Set은 동일한 값을 저장하지 않음 <br>
     * 즉, 동일한 유저를 받지 않기 위함.*/

    private final Set<WebSocketSession> sessions = new HashSet<>();

    /**set값을 변형 시키지 않고 유지 하기 위한 직접 빌더를 정의한다.*/
    @Builder(toBuilder = true)
    public ChatRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public void handleActions(WebSocketSession session, ChatMessage message, ChatService chatService) {
        if (message.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            message.toBuilder()
                    .message(message.getSender() + "님이 입장 하셨습니다.");
        }
        sendMessage(message, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session ->
                chatService.sendMessage(session, message)
        );
    }
}
