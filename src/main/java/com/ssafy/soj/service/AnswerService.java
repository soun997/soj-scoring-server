package com.ssafy.soj.service;

import com.ssafy.soj.domain.entity.Answer;
import com.ssafy.soj.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Long submit(Answer answer) {
        //TODO: 코드 validation 로직 작성
        answer.scoring();
        answerRepository.save(answer);
        answer.getUser().getAnswers().add(answer);
        answer.getProblem().getAnswers().add(answer);

        return answer.getSeq();
    }

    public Answer findBySeq(Long seq) {
        return answerRepository.findBySeq(seq);
    }

    public List<Answer> findAll(Long problemSeq) {
        return answerRepository.findAll(problemSeq);
    }
}
