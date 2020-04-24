package com.palmseung.modules.users.service;

import com.palmseung.modules.users.domain.User;
import com.palmseung.modules.users.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }
}
