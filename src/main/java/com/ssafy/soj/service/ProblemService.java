package com.ssafy.soj.service;


import com.ssafy.soj.domain.entity.Problem;
import com.ssafy.soj.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    @Transactional
    public Long regist(Problem problem) {
        problemRepository.save(problem);

        return problem.getSeq();
    }

    public Problem findBySeq(Long seq) {
        return problemRepository.findBySeq(seq);
    }
}
