package com.justudy.backend.login.service;

import com.justudy.backend.exception.InvalidRequest;
import com.justudy.backend.login.dto.request.LoginRequest;
import com.justudy.backend.login.dto.response.LoginResult;
import com.justudy.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public LoginResult loginProcess(LoginRequest loginRequest) {
        LoginResult loginInfo = memberRepository.findLoginInfoByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new InvalidRequest("userId", "잘못된 아이디입니다."));
        String originPassword = loginInfo.getPassword();
        validatePassword(loginRequest, originPassword);

        return loginInfo;
    }


    private void validatePassword(LoginRequest request, String encodedPassword) {
        boolean matches = passwordEncoder.matches(request.getPassword(), encodedPassword);

        if (!matches) {
            throw new InvalidRequest("password", "비밀번호가 잘못되었습니다.");
        }
    }

}
