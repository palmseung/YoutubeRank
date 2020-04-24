package com.palmseung.modules.users.service;

import com.palmseung.modules.users.domain.User;
import com.palmseung.modules.users.domain.UserRepository;
import com.palmseung.modules.users.dto.CreateUserRequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(CreateUserRequestView requestView) {
        String encodedPassword = passwordEncoder.encode(requestView.getPassword());
        requestView.changePassword(encodedPassword);
        return userRepository.save(requestView.toEntity());
    }
}
