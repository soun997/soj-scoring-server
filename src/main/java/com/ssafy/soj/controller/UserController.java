package com.ssafy.soj.controller;

import com.ssafy.soj.domain.dto.LoginForm;
import com.ssafy.soj.domain.dto.RegistryForm;
import com.ssafy.soj.domain.entity.User;
import com.ssafy.soj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/registry")
    public User registry(@RequestBody RegistryForm form) {
        User user = new User();
        user.setId(form.getUserId());
        user.setPassword(form.getUserPw());
        user.setName(form.getUserName());

        Long savedSeq = userService.join(user);

        return userService.findBySeq(savedSeq);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> registry(@RequestBody LoginForm form) {
        User user = new User();
        user.setId(form.getUserId());
        user.setPassword(form.getUserPw());

        Long savedSeq = userService.login(user);
        if (savedSeq == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userService.findBySeq(savedSeq), HttpStatus.OK);
    }
}
