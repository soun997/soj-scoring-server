package com.ssafy.soj.service;


import com.ssafy.soj.domain.dto.LoginForm;
import com.ssafy.soj.domain.entity.User;
import com.ssafy.soj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        // TODO: 회원가입 validation 로직 추가
        userRepository.save(user);

        return user.getSeq();
    }

    @Transactional
    public Long login(User user) {
        User loginUser = findById(user.getId());
        if (loginUser == null) {
            return null;
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            return null;
        }

        return loginUser.getSeq();
    }

    public User findBySeq(Long seq) {
        return userRepository.findBySeq(seq);
    }

    public User findById(String id) {
        return userRepository.findById(id);
    }
}
