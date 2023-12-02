package com.example.redist.chat.redistest.True.Redis.chatDTO;

import lombok.Builder;
import lombok.Getter;

/**빌더를 유의해서 봐주세요 toBuilder는 기존 값을 유지하는게 가능한 빌더 패턴입니다.
 * ChatRoom::handleActions 에서 아주 유용하게 사용하는 예시가 있습니다. */
@Getter
@Builder(toBuilder = true)
public class ChatMessage {

    /**이넘 타입을 설정하는 이유는 방 생성과 기존 방에 대화를 구분하기 위함이다.
     * ENTER는 새로운 방을 생성 혹은 입장 하기 위한 타입이고
     * TALK는 새로운 채팅을 인식하기 위한 타입이다.*/
    public enum MessageType {ENTER, TALK}

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
