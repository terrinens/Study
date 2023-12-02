package com.example.redist.chat.redistest.First.Redis.controller;

import com.example.redist.chat.redistest.First.Redis.chatDTO.ChatRoom;
import com.example.redist.chat.redistest.First.Redis.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String newRoomName) {
        return chatService.createRoom(newRoomName);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}
