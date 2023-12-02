package com.entity.test;

import com.entity.test.entity.TestBoard_1;
import com.entity.test.repository.TestBoard_1_Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class TestApplicationTests {

    private final Random random = new Random();
    @Autowired
    private TestBoard_1_Repository testBoard1Repository;

    @Test
    @DisplayName("테스트 보드1 더미 데이터")
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            TestBoard_1 testBoard1 = TestBoard_1.builder()
                    .title("testTitle" + i)
                    .view(random.nextInt())
                    .con("testCon" + i)
                    .build();
            testBoard1Repository.save(testBoard1);
        }
    }

}
