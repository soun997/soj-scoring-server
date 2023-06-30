package com.ssafy.soj.service;

import com.ssafy.soj.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception {
        // given
        User user = new User();
        user.setId("sypark");
        user.setPassword("1234");
        user.setName("박소윤");

        Long savedSeq = userService.join(user);

        // then
        assertEquals(user, userService.findBySeq(savedSeq));
    }
}
