package com.example.tddexercise.ch02;

public class PasswordStrengthMeter {
	public PasswordStrength meter(String password) {
		// 14. INVALID를 반환하는 테스트코드
		if (password == null || password.isEmpty()) {
			return PasswordStrength.INVALID;
		}

		// 3. meter가 STRONG을 리턴하도록 바꾼다.
		// return PasswordStrength.STRONG;

		// 5. NORMAL을 리턴하도록 바꾼다면 두 번째 테스트만 통과한다
		// -> 따라서 두 테스트를 모두 통과할 코드를 작성한다.

		// 7. 암호가 숫자를 포함했는지를 판단하는 코드를 구현한다.
		//		boolean containsNumber = false;
		//		for (char ch : password.toCharArray()) {
		//			if (ch >= '0' && ch <= '9') {
		//				containsNumber = true;
		//				break;
		//			}
		//		}
		// 8. 코드가 다소 길어지므로 해당 코드를 추출해서 가독성 개선, 메서드 길이를 줄임

		/*
			17. 길이가 8이상인지 여부를 확인할 수 있는 방식으로 코드를 바꾼다.
		   	그리고 조건에 따른 로직을 검증하도록 코드의 순서를 바꿔주자.
		 */
		//		if (password.length() < 8) {
		//			return PasswordStrength.NORMAL;
		//		}

		// 25. metCounts라는 변수를 만들어서 리팩토링을 한다.
		// 28. metCounts에 관한 메서드를 따로 만들어주면 가독성이 증가한다.
		int metCounts = getMetCriteriaCounts(password);

		if (metCounts <= 1) {
			// 27. 아무 조건도 만족 못했을 때의 조건을 추가
			return PasswordStrength.WEAK;
		}
		if (metCounts == 2) {
			return PasswordStrength.NORMAL;
		}
		return PasswordStrength.STRONG;

		// 18. if문의 위치를 옮겼습니다. if문의 위치를 이동한 이유는
		// 1) 개별 규칙을 검사하는 로직, 2) 규칙을 검사한 결과에 따라 암호 강도를 계산하는 로직
		// 이 두 로직을 구분해서 모으기 위함이다.

		// 24. 아래의 if문이 가독성을 떨어뜨리고 있으므로 아래의 코드를 리팩토링해준다.
		//		if (lengthEnough && !containsNumber && !containsUpp) {
		//			// 19. 다시 테스트를 실행해도 테스트는 실패한다.
		//			// 이제 새로 추가한 테스트를 통과시키기 위한 코드를 구현한다.
		//			return PasswordStrength.WEAK;
		//		}
		//		if (!lengthEnough && containsNumber && !containsUpp) {
		//			// 21. 숫자 조건만 만족시킬 때 통과하는 코드를 구현한다.
		//			return PasswordStrength.WEAK;
		//		}
		//		if (!lengthEnough && !containsNumber && containsUpp) {
		//			// 23. 숫자 조건만 만족시킬 때 통과하는 코드를 구현한다.
		//			return PasswordStrength.WEAK;
		//		}

		// 리팩토링에 의해 주석처리합니다.
		//		if (!lengthEnough) {
		//			return PasswordStrength.NORMAL;
		//		}
		//		if (!containsNumber) {
		//			return PasswordStrength.NORMAL;
		//		}
		//		// 15. 코드가 길어지기 때문에 따로 메서드로 뺐다.
		//		if (!containsUpp) {
		//			return PasswordStrength.NORMAL;
		//		}
		//		return PasswordStrength.STRONG;
	}
	// 29. 코드의 작성이 모두 끝났기 때문에 완성된 코드를 배포 대상이 되는 폴더로 이동시킨다.
	// 이것에 대해서는 주석을 제거한 리팩토리 버전 코드를 만들도록 하겠다.

	// getMetCriteriaCounts메서드를 작성해서 가독성을 높였다.
	private int getMetCriteriaCounts(String password) {
		int metCounts = 0;

		if (password.length() >= 8) {
			metCounts++;
		}
		if (meetContainingNumberCriteria(password)) {
			metCounts++;
		}
		if (meetContainingUpperCaseCriteria(password)) {
			metCounts++;
		}

		return metCounts;
	}

	private boolean meetContainingUpperCaseCriteria(String password) {
		for (char ch : password.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				return true;
			}
		}
		return false;
	}

	private boolean meetContainingNumberCriteria(String password) {
		for (char ch : password.toCharArray()) {
			if (ch >= '0' && ch <= '9') {
				return true;
			}
		}
		return false;
	}
}
