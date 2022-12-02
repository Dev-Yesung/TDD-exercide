package com.example.tddexercise.ch02;

public class PasswordStrengthMeter {
	public PasswordStrength meter(String password) {
		// 3. meter가 STRONG을 리턴하도록 바꾼다.
		// return PasswordStrength.STRONG;

		// 5. NORMAL을 리턴하도록 바꾼다면 두 번째 테스트만 통과한다
		// -> 따라서 두 테스트를 모두 통과할 코드를 작성한다.
		if (password.length() < 8) {
			return PasswordStrength.NORMAL;
		}
		return PasswordStrength.STRONG;
	}
}
