package com.palmseung.modules.user;

import com.palmseung.modules.users.domain.User;
import com.palmseung.modules.users.domain.UserRepository;
import com.palmseung.modules.users.dto.CreateUserRequestView;
import com.palmseung.modules.users.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.palmseung.modules.users.UserConstant.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("create메소드가 호출되면, userRepository의 save 메소드가 1번 호출된다.")
    @Test
    public void create() {
        //given
        CreateUserRequestView requestView = createRequestView();

        //when
        userService.create(requestView);

        //then
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @DisplayName("create 메소드가 호출되면, Passwornd를 encoding하는 메소드가 호출된다.")
    @Test
    public void encodePassword() {
        //given
        CreateUserRequestView requestView = createRequestView();

        //when
        userService.create(requestView);

        //then
        verify(passwordEncoder, times(1))
                .encode(TEST_PASSWORD);
    }

    private CreateUserRequestView createRequestView() {
        return CreateUserRequestView.builder()
                .email(TEST_EMAIL)
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .build();
    }
}