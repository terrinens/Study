package com.example.redist.chat.redistest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSockConfig implements WebSocketConfigurer {
    
    private final WebSocketHandler webSocketHandler;

    /**addHandler는  엔드 포인트를 지정한다. 엔드 포인트란 맵핑 주소를 말한다. <br>
     * 예시로는 /proj/chat 이다. <br>
     * setAllowedOrigins는 접속 가능한 아이피를 지정한다. <br>
     * 이때 * 는 어느 프로그래밍 언어나 동일한 의미로 사용되는 (All)이라는 뜻이다. <br> */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
    }
}
