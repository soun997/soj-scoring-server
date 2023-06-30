package com.ssafy.soj.controller;

import com.ssafy.soj.domain.dto.ProblemForm;
import com.ssafy.soj.domain.dto.RegistryForm;
import com.ssafy.soj.domain.entity.Problem;
import com.ssafy.soj.domain.entity.User;
import com.ssafy.soj.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/problems/create")
    public Problem registry(@RequestBody ProblemForm form) {
        Problem problem = new Problem();
        problem.setDescription(form.getDescription());
        problem.setInput(form.getInput());
        problem.setOutput(form.getOutput());

        Long savedSeq = problemService.regist(problem);

        return problemService.findBySeq(savedSeq);
    }
}
