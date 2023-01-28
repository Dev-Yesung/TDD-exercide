package com.example.tddexercise.ch03;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

    @Test
    @DisplayName("만원 납부하면 한달 뒤가 만료일이 됨")
    void pay_10000_won() {
        // test example 1
        assertExpiryDate(
                new PayDate(LocalDate.of(2019, 3, 1),
                            10000),
                LocalDate.of(2019, 4, 1));
        // test example 2
        assertExpiryDate(
                PayDate.builder()
                       .billingDate(LocalDate.of(2019, 5, 5))
                       .payAmount(10_000)
                       .build(),
                LocalDate.of(2019, 6, 5));
    }

    @Test
    @DisplayName("납부일과 한달 뒤 일자가 같지 않음")
    void not_equal_to_a_month_after() {
        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 1, 31))
                                .payAmount(10_000)
                                .build(),
                         LocalDate.of(2019, 2, 28));

        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 5, 31))
                                .payAmount(10_000)
                                .build(),
                         LocalDate.of(2019, 6, 30));

        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2020, 1, 31))
                                .payAmount(10_000)
                                .build(),
                         LocalDate.of(2020, 2, 29));
    }

    @Test
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 만원 납부")
    void not_equal_to_validate_day_from_first_pay_day_when_pay_10000_won() {
        PayDate payDate = PayDate.builder().firstBillingDate(LocalDate.of(2019, 1, 31))
                                 .billingDate(LocalDate.of(2019, 2, 28))
                                 .payAmount(10_000)
                                 .build();

        assertExpiryDate(payDate, LocalDate.of(2019, 3, 31));

        PayDate payDate2 = PayDate.builder()
                                  .firstBillingDate(LocalDate.of(2019, 1, 30))
                                  .billingDate(LocalDate.of(2019, 2, 28))
                                  .payAmount(10_000)
                                  .build();

        assertExpiryDate(payDate2, LocalDate.of(2019, 3, 30));

        PayDate payDate3 = PayDate.builder()
                                  .firstBillingDate(LocalDate.of(2019, 5, 31))
                                  .billingDate(LocalDate.of(2019, 6, 30))
                                  .payAmount(10_000)
                                  .build();

        assertExpiryDate(payDate3, LocalDate.of(2019, 7, 31));
    }

    @Test
    @DisplayName("2만원 이상 납부하면 비례해서 만료일 계산")
    void pay_money_more_than_20000_won() {
        assertExpiryDate(
                PayDate.builder()
                       .billingDate(LocalDate.of(2019, 3, 1))
                       .payAmount(20_000)
                       .build(),
                LocalDate.of(2019, 5, 1)
        );

        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 3, 1))
                                .payAmount(30000)
                                .build(),
                         LocalDate.of(2019, 6, 1)
        );

        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 3, 1))
                                .payAmount(50000)
                                .build(),
                         LocalDate.of(2019, 8, 1)
        );

        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 3, 1))
                                .payAmount(70000)
                                .build(),
                         LocalDate.of(2019, 10, 1)
        );
    }

    @Test
    @DisplayName("첫 납부일과 만료일 일자가 다를 때 2만원 이상 납부")
    void not_equal_to_validate_day_from_first_pay_day_when_pay_more_than_20000_won() {
        assertExpiryDate(PayDate.builder()
                                .firstBillingDate(LocalDate.of(2019, 1, 31))
                                .billingDate(LocalDate.of(2019, 2, 28))
                                .payAmount(20_000)
                                .build(),
                         LocalDate.of(2019, 4, 30));

        assertExpiryDate(PayDate.builder()
                                .firstBillingDate(LocalDate.of(2019, 1, 31))
                                .billingDate(LocalDate.of(2019, 2, 28))
                                .payAmount(40_000)
                                .build(),
                         LocalDate.of(2019, 6, 30)
        );

        assertExpiryDate(PayDate.builder()
                                .firstBillingDate(LocalDate.of(2019, 3, 31))
                                .billingDate(LocalDate.of(2019, 4, 30))
                                .payAmount(30_000)
                                .build(),
                         LocalDate.of(2019, 7, 31)
        );
    }

    @Test
    @DisplayName("10만원을 납부하면 1년 제공")
    void renew_validate_one_year_when_pay_100_000() {
        assertExpiryDate(PayDate.builder()
                                .billingDate(LocalDate.of(2019, 1, 28))
                                .payAmount(100_000)
                                .build(),
                         LocalDate.of(2020, 1, 28));
    }

    @Test
    @DisplayName("윤달 마지막 날에 10만원을 납부하는 경우")
    void pay_100_000_when_reap_month() {
        // todo: 2020년 2월 29일과 같은 윤달 마지막 날에 10만원을 납부하는 경우 테스트 구현
    }

    @Test
    @DisplayName("10만원보다 더 많은 금액을 납부하는 경우")
    void pay_more_than_100_000() {
        // todo: 13만원을 납부하는 경우 1년 3개월 뒤가 만료일이 되어야 한다.
    }

    private void assertExpiryDate(PayDate payDate, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payDate);

        assertThat(realExpiryDate).isEqualTo(expectedExpiryDate);
    }
}
