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

	/*
	지금까지 추가된 세 개의 테스트 메서드는 아래와 같은 형태를 갖는다.
	@Test void 메서드이름() {
		PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();
		PasswordStrength result = passwordStrengthMeter.meter(암호);
		Assertions.assertEquals(PasswordStrength.값, result);
	}
	테스트 코드도 코드이기 때문에 유지보수 대상이다.
	즉, 테스트 메서드에서 발생하는 중복을 알맞게 제거하거나 의미가 잘 드러나게 코드를 수정할 필요가 있다.

	9. 인스턴스 생성 코드가 중복되므로
	   PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter()
	   를 공동으로 사용하도록 뺀다.

	10. 코드를 수정하고나서 테스트를 다시 실행한다.
	    -> 코드 수정으로 인해 테스트가 깨지지 않았는지 확인하기 위함이다.

	11. 없앨 수 있는 중복코드가 하나 더 있다.
	PasswordStrength result = passwordStrengthMeter.meter(암호);
	Assertions.assertEquals(기대값, result);
	이런 부류의 중복은 메서드를 이용해 제거할 수 있다.

	** 테스트 코드의 중복을 무턱대고 제거하면 안된다.
	중복을 제거한 뒤에도 테스트 코드의 가독성이 떨어지지 않고 수정이 용이한 경우에만 중복을 제거해야 한다.
	중복을 제거한 뒤에 오히려 테스트 코드 관리가 어려워진다면 제거했던 중복을 되돌려야 한다.
 */

	// 9. 인스턴스 생성 코드가 중복되므로 공통코드로 만들어주었다.
	private PasswordStrengthMeter passwordStrengthMeter = new PasswordStrengthMeter();

	// 12. 다음과 같은 assertStrength라는 메서드를 추가해서 중복을 제거할 수 있다.
	private void assertStrength(String password, PasswordStrength expStr) {
		PasswordStrength result = passwordStrengthMeter.meter(password);
		Assertions.assertEquals(expStr, result);
	}

	@DisplayName("모든 규칙을 충족하는 경우") @Test void all_criteria_then_strong() {

		// 2. 기대한 값은 'STRONG' 하지만 실제 값은 null -> 테스트 실패
		// PasswordStrength result = passwordStrengthMeter.meter("ab12!@AB");

		/*
			Assertions.assertEquals()메서드는 두 값이 같은지 비교하는 단언(assertion)
			단언은 값이 특정 조건을 충족하는지 확인하고 충족하지 않는 경우 익셉션을 발생시킨다
			통과하지 못하고 익셉션이 발생하면 테스트 실패
		 */

		// 1. 컴파일 에러를 없앨만큼만 클래스를 작성
		// Assertions.assertEquals(PasswordStrength.STRONG, result);

		// 12에서 작성한 메서드를 이용한 리팩토링
		assertStrength("ab12!@AB", PasswordStrength.STRONG);

		// +a) 테스트 메서드에 모든 규칙을 충족하는 예를 하나 더 추가
		// PasswordStrength result2 = passwordStrengthMeter.meter("abc1!Add");
		// Assertions.assertEquals(PasswordStrength.STRONG, result2);

		// 12에서 작성한 메서드를 이용한 리팩토링
		assertStrength("abc1!Add", PasswordStrength.STRONG);
	}

	@DisplayName("길이가 8미만이고 나머지 조건은 충족") @Test void except_length_then_normal() {
		// 4.NORMAL이 없으므로 컴파일에러 -> NORMAL을 구현한다.
		//Assertions.assertEquals(PasswordStrength.NORMAL, result);

		// 12에서 작성한 메서드를 이용한 리팩토링
		assertStrength("ab12!@A", PasswordStrength.NORMAL);

		// +a) 검증코드 추가
		// PasswordStrength result2 = passwordStrengthMeter.meter("Ab12!c");
		// Assertions.assertEquals(PasswordStrength.NORMAL, result2);

		// 12에서 작성한 메서드를 이용한 리팩토링
		assertStrength("Ab12!c", PasswordStrength.NORMAL);
	}

	@DisplayName("숫자를 포함하지 않고 나머지 조건은 충족") @Test void except_number_then_normal() {
		// 6. 숫자를 포함하지 않고 나머지 조건을 충족하는 테스트를 작성
		// PasswordStrength result = passwordStrengthMeter.meter("ab!@ABqwer");
		// Assertions.assertEquals(PasswordStrength.NORMAL, result);

		// 12에서 작성한 메서드를 이용한 리팩토링
		assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
	}

	/*
		테스트 코드를 작성하는 과정에서 값이 없는 경우를 테스트하지 않았다.
	 	이런 예외상황을 고려하지 않으면 소프트웨어가 비정상적으로 동작하게 된다.
		예를 들어 NPE(NullPointerException)이 바생하게 된다.
		null에 대해서도 알맞게 동작해야 한다.
		이 책에서는 유효하지 않은 암호를 의미하는 PasswordStrength.INVALID를 리턴하는 방식을 사용
	 */

	@DisplayName("입력이 null인 경우에 대한 테스트 추가") @Test void null_input_then_invalid() {
		// 13. 당연히 테스트에 실패한다. 비밀번호에 null 혹은 empty 문자열이 들어왔을 경우 INVALID를 반환하는 코드를 작성해야한다.
		assertStrength(null, PasswordStrength.INVALID);
	}

	@DisplayName("대문자를 포함하지 않고 나머지 조건은 충족하는 경우") @Test void except_for_uppercase_then_normal() {
		// 테스트를 실행하면 당연히 실패한다. 따라서 실패한 코드를 통과시킬 코드를 작성하자.
		assertStrength("ab12!@df", PasswordStrength.NORMAL);
	}

	// 이제 남은 조건은 한 가지 조건만 충족하거나 모든 조건을 충족하지 않은 경우이다.
	// 16. 먼저 길이가 8글자 이상인 조건만 충족하는 경우를 진행해보자.
	@DisplayName("길이가 8글자 이상인 조건만 충족하는 경우") @Test void only_length_criteria_then_weak() {
		assertStrength("abdefghi", PasswordStrength.WEAK);
		/* 	여기서 주목할 것은 테스트 결과가 WEAK가 아니라 NORMAL이어서 테스트에 실패했다는 것이다.
			즉, 테스트를 통과시키려면 세 조건 중에 길이 조건 충족 -> 나머지 두 조건은 충족X == WEAK반환
		 */
	}

	// 20. 숫자 포함 조건만 충족하는 경우를 테스트해보자
	@DisplayName("숫자 포함 조건만 충족하는 경우") @Test void only_number_criteria_then_weak() {
		assertStrength("12345", PasswordStrength.WEAK);
	}

	// 22. 이번에는 대문자 포함 조건만 충족하는 경우를 검증하는 테스트를 추가하자
	@DisplayName("대문자 포함 조건만 충족하는 경우") @Test void only_upper_criteria_then_weak() {
		assertStrength("ABZEF", PasswordStrength.WEAK);
	}

	// 26. 아무 조건도 충족하지 않는 경우를 검증하는 테스트를 추가해야한다.
	// -> 대문자x, 길이 8미만, 숫자포함x
	@DisplayName("아무 조건도 충족하지 않은 경우") @Test void no_criteria_then_weak() {
		assertStrength("abc", PasswordStrength.WEAK);
	}
}

/*
	* 결론 *
	[ TDD의 흐름 : 테스트 -> 코딩 -> 리팩토링 ]

	1) TDD는 테스트를 먼저 작성하고 테스트를 통과하지 못하면 테스트를 통과시킬 만큼 코드를 작성한다.
	2) 테스트를 통과한 뒤에는 개선할 코드가 있다면 리팩토링을 한다.
	3) 리팩토링을 수행한 뒤에는 다시 테스트를 실행해서 기존 기능이 망가지지 않았는지 확인한다.

	** TDD 사이클을 레드(Red)-그린(Green)-리팩터(Refactor)로 부르기도 한다.
	* 여기서 레드는 실패하는 테스트를 의미한다. -> 테스트가 실패하면 빨간 글씨로 알려주는데서 비롯됨
	* 그린은 성공한 테스트를 의미한다.
	* 리팩터는 말 그대로 리팩토링을 의미한다.

	* 테스트 코드를 먼저 작성하면 테스트가 개발을 주도하게 된다.
	가장 먼저 통과해야할 테스트를 작성했고 그 과정 중에 구현을 생각하지 않고
	단지 해당 기능이 올바르게 동작하는지 검증할 수 있는 테스트 코드를 만들었을 뿐이다.
	테스트를 추가한 뒤에는 테스트를 통과시킬 만큼 기능을 구현했고 아직 추가하지 않은 테스트를 고려해서 구현하지 않았다.
	테스트 코드를 만들면 다음 개발 범위가 정해진다. 테스트 코드가 추가되면서 검증하는 범위가 넓어질수록 구현도 점점 완성되어간다.

	* 리팩토링에 대해
	구현을 완료한 뒤에 리팩토링할 대상이 눈에 들어오면 리팩토링을 하면서 코드를 정리한다.
	당장 리팩토링을 어떻게 할지 생각이 나지 않으면 다음 테스트를 진행했다.
	테스트 코드 자체도 리팩토링 대상에 넣었다.
	당장 리팩토링을 진행하지 않더라도 테스트 코드가 있다면 리팩토링을 보다 과감하게 진행할 수 있다.
	잘 동작하는 코드를 수정하는 것은 심리적으로 불안감을 줘 코드 수정을 꺼리게 만들지만
	해당 기능이 온전하게 동작한다는 것을 검증해주는 테스트가 있다면 코드 수정에 대한 심리적 불안감을 줄여준다.
	즉, 리팩토링을 원활하게 할 수 있게 도와준다.
	TDD는 개발 과정에서 지속적으로 코드를 정리하므로 코드 품질이 급격히 나빠지지 않게 막아주는 효과가 있다.

	* 빠른 피드백
	TDD는 코드 수정에 대한 피드백이 빠르다.
	새로운 코드를 추가하거나 기존 코드를 수정하더라도 테스트를 돌려 해당 코드가 올바른지 바로 확인할 수 있다.
	즉, 잘못된 코드가 배포되는 것을 방지한다.
 */
