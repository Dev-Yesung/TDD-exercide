package com.example.tddexercise.ch02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
	암호 검사기 규칙
	-> 규칙을 준수하는지에 따라 '약함', '보통', '강함'으로 구분
	1. 길이가 8글자 이상
	2. 0부터 9 사이의 숫자를 포함
	3. 대문자 포함

	3개의 규칙을 모두 충족하면 암호는 '강함'
	2개의 규칙을 충족하면 암호는 '보통'
	1개 이하의 규칙을 충족하면 암호는 '약함'
*/
public class PasswordStrengthMeterTest {
	/* Tip : 첫 번째 테스트를 만들 때는 가장 쉽거나 가장 예외적인 상황을 선택한다.

	   모든 규칙을 충족하는 경우 || 모든 조건을 충족하지 않는 경우

	   모든 조건을 충족하지 않는 경우
	   -> 각 조건을 검사하는 코드를 모두 구현해야 한다.
	   -> 한 번에 만드어야 할 코드가 많아진다. 즉, 모든 구현을 다하고 하는 테스트와 같다.

	   모든 규칙을 충족하는 경우
	   -> 각 조건을 검사하는 코드를 만들지 않고 '강함'에 해당하는 값을 리턴하면 테스트 통과
	 */
	@DisplayName("모든 규칙을 충족하는 경우") @Test void all_criteria_then_strong() {
		PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();
		// 2. 기대한 값은 'STRONG' 하지만 실제 값은 null -> 테스트 실패
		PasswordStrength result = passwordStrengthMeter.meter("ab12!@AB");

		/*
			Assertions.assertEquals()메서드는 두 값이 같은지 비교하는 단언(assertion)
			단언은 값이 특정 조건을 충족하는지 확인하고 충족하지 않는 경우 익셉션을 발생시킨다
			통과하지 못하고 익셉션이 발생하면 테스트 실패
		 */

		// 1. 컴파일 에러를 없앨만큼만 클래스를 작성
		Assertions.assertEquals(PasswordStrength.STRONG, result);
		// +a) 테스트 메서드에 모든 규칙을 충족하는 예를 하나 더 추가
		PasswordStrength result2 = passwordStrengthMeter.meter("abc1!Add");
		Assertions.assertEquals(PasswordStrength.STRONG, result2);
	}

	@DisplayName("길이가 8미만이고 나머지 조건은 충족") @Test void except_length_then_normal() {
		PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();
		PasswordStrength result = passwordStrengthMeter.meter("ab12!@A");
		// 4.NORMAL이 없으므로 컴파일에러 -> NORMAL을 구현한다.
		Assertions.assertEquals(PasswordStrength.NORMAL, result);
		// +a) 검증코드 추가
		PasswordStrength result2 = passwordStrengthMeter.meter("Ab12!c");
		Assertions.assertEquals(PasswordStrength.NORMAL, result2);
	}

	@DisplayName("숫자를 포함하지 않고 나머지 조건은 충족") @Test void except_number_then_normal() {
		PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();
		PasswordStrength result = passwordStrengthMeter.meter("ab12!@A");
	}
}
