package com.example.tddexercise.ch07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

public class UserRegisterMockTest {

    private UserRegister userRegister;
    private WeakPasswordChecker mockPasswordChecker = Mockito.mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private EmailNotifier mockEmailNotifier = Mockito.mock(EmailNotifier.class);

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker, fakeRepository, mockEmailNotifier);
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    void weakPassword() {
        // given : "pw"인자를 사용해서 모의 객체의 checkPasswordWeak 메서드를 호출하면,
        // willReturn : 결과로 "true"를 리턴하라
        BDDMockito.given(mockPasswordChecker.checkPasswordWeak("pw")).willReturn(true);

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw", "email");
        }, "약한 암호면 가입 실패", WeakPasswordException.class);
    }

    @Test
    @DisplayName("회원 가입시 암호 검사 수행함")
    void checkPassword() {
        userRegister.register("id", "pw", "email");

        BDDMockito.then(mockPasswordChecker).should().checkPasswordWeak(BDDMockito.anyString());
    }

    @Test
    @DisplayName("가입하면 메일을 전송함")
    void whenRegister() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        BDDMockito.then(mockEmailNotifier).should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertThat(realEmail).isEqualTo("email@email.com");
    }
}
