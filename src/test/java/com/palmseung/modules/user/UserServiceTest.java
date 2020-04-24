package com.palmseung.modules.user;

import com.palmseung.modules.users.domain.User;
import com.palmseung.modules.users.domain.UserRepository;
import com.palmseung.modules.users.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.palmseung.modules.users.UserConstant.TEST_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserService.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
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