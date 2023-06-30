package com.ssafy.soj.repository;

import com.ssafy.soj.domain.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnswerRepository {

    private final EntityManager em;

    public void save(Answer answer) {
        em.persist(answer);
    }

    public Answer findBySeq(Long seq) {
        return em.find(Answer.class, seq);
    }

    public List<Answer> findAll(Long problemSeq) {
        return em.createQuery("select a from Answer a where a.problem.seq = :problemSeq")
                .setParameter("problemSeq", problemSeq).getResultList();
    }
}
