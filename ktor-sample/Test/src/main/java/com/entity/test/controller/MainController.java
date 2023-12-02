package com.entity.test.controller;

import com.entity.test.entity.TestBoard_1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor @RestController
public class MainController {

    @GetMapping("/")
    public String index() {
        TestBoard_1 board1 = TestBoard_1.builder()
                .title("testTitle")
                .con("testCon")
                .view(1234)
                .build();
        return "testSuccess";
    }
}
