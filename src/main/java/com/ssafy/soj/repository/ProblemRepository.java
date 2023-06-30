package com.ssafy.soj.repository;

import com.ssafy.soj.domain.entity.Problem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProblemRepository {

    private final EntityManager em;

    public void save(Problem problem) {
        em.persist(problem);
    }

    public Problem findBySeq(Long seq) {
        return em.find(Problem.class, seq);
    }
}
