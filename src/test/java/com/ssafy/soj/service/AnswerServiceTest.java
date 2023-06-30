package com.ssafy.soj.service;

import com.ssafy.soj.domain.entity.Answer;
import com.ssafy.soj.domain.entity.Problem;
import com.ssafy.soj.domain.ScoringResult;
import com.ssafy.soj.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AnswerServiceTest {

    @Autowired
    AnswerService answerService;
    @Autowired
    UserService userService;
    @Autowired
    ProblemService problemService;

    @Test
    @Rollback(false)
    public void 답안제출() throws Exception {
        // given
        Answer answer = createAnswer();

        Long savedSeq = answerService.submit(answer);

        // then
        assertEquals(answer, answerService.findBySeq(savedSeq));
    }

    @Test
    @Rollback(false)
    public void 정답채점() throws Exception {
        // given
        Answer answer = createAnswer();

        Long savedSeq = answerService.submit(answer);

        // then
        assertEquals(answer.getResult(), ScoringResult.CORRECT);
    }

    private User createUser(){
        User user = new User();
        user.setId("sypark");
        user.setName("박소윤");

        return user;
    }

    private Problem createProblem() {
        Problem problem = new Problem();
        problem.setDescription(
                "두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오. (0 < A,B < 10)\n" +
                        "예제 입력\n " +
                        "1 2\n" +
                        "예제 출력\n" +
                        "3\n ");

        problem.setInput("1 2");
        problem.setOutput("3");

        return problem;
    }

    private Answer createAnswer() {
        User user = createUser();
        userService.join(user);
        Problem problem = createProblem();
        problemService.regist(problem);

        Answer answer = new Answer();
        answer.setUser(user);
        answer.setProblem(problem);
        answer.setCode("import java.io.*;\n" +
                "\n" +
                "public class Main{\n" +
                "    public static void main(String[] args) throws Exception {\n" +
                "        \n" +
                "        int num1, num2;\n" +
                "        \n" +
                "        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));\n" +
                "        num1 = Integer.parseInt(br.readLine());\n" +
                "        num2 = Integer.parseInt(br.readLine());\n" +
                "        \n" +
                "        System.out.println(num1 + num2);\n" +
                "    }\n" +
                "}");

        return answer;
    }
}
