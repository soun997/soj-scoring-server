package com.ssafy.soj.controller;

import com.ssafy.soj.domain.dto.AnswerForm;
import com.ssafy.soj.domain.dto.ProblemForm;
import com.ssafy.soj.domain.entity.Answer;
import com.ssafy.soj.domain.entity.Problem;
import com.ssafy.soj.domain.entity.User;
import com.ssafy.soj.service.AnswerService;
import com.ssafy.soj.service.ProblemService;
import com.ssafy.soj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final ProblemService problemService;
    private final UserService userService;

    @PostMapping("/answers/{problemSeq}/submit/{userSeq}")
    public Answer submit(@PathVariable Long problemSeq, @PathVariable Long userSeq, @RequestBody AnswerForm form) {

        Answer answer = new Answer();
        answer.setCode(form.getCode());
        Problem problem = problemService.findBySeq(problemSeq);
        System.out.println(problem);
        User user = userService.findBySeq(userSeq);
        System.out.println(user);
        answer.setProblem(problem);
        answer.setUser(user);

        Long savedSeq = answerService.submit(answer);

        return answerService.findBySeq(savedSeq);
    }

    @GetMapping("/answers/{problemSeq}")
    public List<Answer> getAnswers(@PathVariable Long problemSeq) {

        return answerService.findAll(problemSeq);
    }
}
