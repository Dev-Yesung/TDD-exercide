package com.example.tddexercise.ch07;

public class UserRegister {

    private WeakPasswordChecker passwordChecker;
    private UserRepository userRepository;
    private EmailNotifier emailNotifier;

    public UserRegister(WeakPasswordChecker passwordChecker, UserRepository userRepository, EmailNotifier emailNotifier) {
        this.passwordChecker = passwordChecker;
        this.userRepository = userRepository;
        this.emailNotifier = emailNotifier;
    }

    public void register(String id, String pw, String email) {
        // 패스워드 체크
        if (passwordChecker.checkPasswordWeak(pw)) {
            throw new WeakPasswordException();
        }
        // 유저 아이디 중복체크
        User user = userRepository.findById(id);
        if (user != null) {
            throw new DupIdException();
        }
        userRepository.save(new User(id, pw, email));

        emailNotifier.sendRegisterEmail(email);
    }
}
