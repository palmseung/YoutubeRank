package com.palmseung.modules.user;

import com.palmseung.modules.users.domain.User;
import com.palmseung.modules.users.domain.UserRepository;
import com.palmseung.modules.users.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.palmseung.modules.users.UserConstant.TEST_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("create메소드가 호출되면, userRepository의 save 메소드가 1번 호출된다.")
    @Test
    void create() {
        //given
        given(userRepository.save(TEST_USER)).willReturn(TEST_USER);

        //when
        userService.create(TEST_USER);

        //then
        verify(userRepository, times(1))
                .save(any(User.class));
    }
}