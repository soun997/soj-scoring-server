package com.ssafy.soj.domain.entity;

import com.ssafy.soj.domain.ScoringResult;
import com.ssafy.soj.domain.compile.JavaStringDynamicCompiler;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.Map;


@Entity
@Table(name = "answer")
@Getter @Setter
public class Answer {

    @Id
    @GeneratedValue
    @Column(name = "answer_seq")
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_seq")
    private Problem problem;

    @Lob
    private String code;

    @Enumerated(EnumType.STRING)
    private ScoringResult result;

    public void scoring() {

        try {
            Map<String, byte[]> compiled = JavaStringDynamicCompiler.getInstance().compile("Main.java", code);
            if (isCorrectAnswer(compiled)) {
                result =  ScoringResult.CORRECT;
                return;
            }
            result =  ScoringResult.INCORRECT;
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
            result = ScoringResult.ERROR;
        }
    }

    private boolean isCorrectAnswer(Map<String, byte[]> compiled) throws Exception{
        String result = "";
        result = execute(compiled).trim();

        if (result.equals(problem.getOutput().trim())){
           return true;
        }
        return false;
    }

    private String execute(Map<String, byte[]> compiled) throws Exception {
        // 컴파일된 클래스 동적 로드
        Class<?> mainClass = JavaStringDynamicCompiler.getInstance().loadClass("Main", compiled);

        Method mainMethod = mainClass.getMethod("main", String[].class);

        String[] input = problem.getInput().split(" ");
        // 문자열 배열을 바이트 배열로 변환
        StringBuilder sb = new StringBuilder();
        for (String str : input) {
            sb.append(str).append("\n"); // 각 문자열을 개행으로 구분하여 연결
        }
        byte[] bytes = sb.toString().getBytes();

        // 바이트 배열을 InputStream으로 변환
        InputStream inputStream = new ByteArrayInputStream(bytes);
        System.setIn(inputStream);

        // 결과를 캡처하기 위한 출력 스트림 생성
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        // System.out을 변경하여 결과를 출력 스트림으로 리디렉션
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        // main() 메서드 실행
        mainMethod.invoke(null, new Object[1]);
        // 출력 결과 가져오기
        String result = outputStream.toString();
        System.setOut(originalPrintStream); // 원래의 출력 스트림으로 복원
        return result;
    }
}
