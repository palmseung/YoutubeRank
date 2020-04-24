package com.palmseung.modules.user;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static com.palmseung.modules.users.UserConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest extends AbstractAcceptanceTest {
    private UserHttpTest userHttpTest;

    @BeforeEach
    void setUp() {
        userHttpTest = new UserHttpTest(webTestClient);
    }

    @DisplayName("회원_가입하기")
    @Test
    public void signUp() {
        //when
        Long id = userHttpTest.createUser(TEST_EMAIL, TEST_NAME, TEST_PASSWORD)
                .getResponseBody()
                .getId();

        //then
        assertThat(id).isEqualTo(1L);
    }
}