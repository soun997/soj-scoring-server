package com.ssafy.soj.service;


import com.ssafy.soj.domain.entity.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ProblemServiceTest {

    @Autowired
    ProblemService problemService;

    @Test
    @Rollback(false)
    public void 문제등록() throws Exception {
        // given
        Problem problem = new Problem();
        problem.setDescription(
                "두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오. (0 < A,B < 10)\n" +
                "예제 입력\n " +
                "1 2\n" +
                "예제 출력\n" +
                "3\n ");

        problem.setInput("1 2");
        problem.setOutput("3");

        Long savedSeq = problemService.regist(problem);

        // then
        assertEquals(problem, problemService.findBySeq(savedSeq));
    }
}
