package com.ssafy.soj.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Problem {

    @Id
    @GeneratedValue
    @Column(name = "problem_seq")
    private Long seq;

    private String description;

    private String input;

    private String output;

    @OneToMany(mappedBy = "problem", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();
}
