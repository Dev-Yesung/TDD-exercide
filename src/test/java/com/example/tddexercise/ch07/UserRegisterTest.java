package com.example.tddexercise.ch07;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserRegisterTest {

    private UserRegister userRegister;
    private StubWeakPasswordChecker stubWeakPasswordChecker = new StubWeakPasswordChecker();
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private SpyEmailNotifier spyEmailNotifier = new SpyEmailNotifier();

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(stubWeakPasswordChecker, fakeRepository, spyEmailNotifier);
    }

    @Test
    @DisplayName("약한 암호면 가입 실패")
    void weakPassword() {
        // stub 으로 하여금 암호 확인 요청이 오면
        // 암호가 약하다고 응답하도록 설정
        stubWeakPasswordChecker.setWeak(true);

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw", "email");
        }, "약한 암호면 가입 실패", WeakPasswordException.class);
    }

    @Test
    @DisplayName("이미 같은 ID가 존재하면 가입 실패")
    void dupIdExists() {
        // 이미 같은 ID 존재하는 상황 만들기
        fakeRepository.save(new User("id", "pw1", "email.@email.com"));

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw2", "email");
        }, "이미 같은 ID가 존재하면 가입 실패", DupIdException.class);
    }

    @Test
    @DisplayName("같은 ID가 없으면 가입 성공함")
    void noDupId_RegisterSuccess() {
        userRegister.register("id", "pw", "email");

        // 가입 결과 확인
        User savedUser = fakeRepository.findById("id");
        assertThat(savedUser.getId()).isEqualTo("id");
        assertThat(savedUser.getEmail()).isEqualTo("email");
    }

    @Test
    @DisplayName("가입하면 메일을 전송함")
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email.com");

        assertThat(spyEmailNotifier.isCalled()).isTrue();
        assertThat(spyEmailNotifier.getEmail()).isEqualTo("email.com");
    }
}
