package com.example.tddexercise.ch03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayDate payDate) {
        int addedMonths = judgeMonths(payDate);

        if (payDate.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payDate, addedMonths);
        }
        return payDate.getBillingDate().plusMonths(addedMonths);
    }

    private int judgeMonths(PayDate payDate) {
        if (payDate.getPayAmount() == 100_000) {
            return 12;
        }
        return payDate.getPayAmount() / 10_000;
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayDate payDate, int addedMonths) {
        LocalDate candidateExp = payDate.getBillingDate().plusMonths(addedMonths);

        if (isNotSameDayOfMonth(candidateExp, payDate.getFirstBillingDate())) {
            return modifyBillingDate(payDate, candidateExp);
        }
        return candidateExp;
    }

    private LocalDate modifyBillingDate(PayDate payDate, LocalDate candidateExp) {
        final int dayLenOfCandiMon = lastDayOfMonth(candidateExp);
        final int dayOfFirstBilling = payDate.getFirstBillingDate().getDayOfMonth();

        if (dayLenOfCandiMon < dayOfFirstBilling) {
            return candidateExp.withDayOfMonth(dayLenOfCandiMon);
        }
        return candidateExp.withDayOfMonth(dayOfFirstBilling);
    }

    private boolean isNotSameDayOfMonth(LocalDate candidateExp, LocalDate dayOfFirstBilling) {
        return dayOfFirstBilling.getDayOfMonth() != candidateExp.getDayOfMonth();
    }

    private int lastDayOfMonth(LocalDate candidateExp) {
        return YearMonth.from(candidateExp).lengthOfMonth();
    }
}
