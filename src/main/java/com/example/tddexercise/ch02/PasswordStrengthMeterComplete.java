package com.example.tddexercise.ch02;

public class PasswordStrengthMeterComplete {
	public PasswordStrength meter(String password) {
		if (password == null || password.isEmpty()) {
			return PasswordStrength.INVALID;
		}

		int metCounts = getMetCriteriaCounts(password);
		if (metCounts <= 1) {
			return PasswordStrength.WEAK;
		}
		if (metCounts == 2) {
			return PasswordStrength.NORMAL;
		}
		return PasswordStrength.STRONG;
	}

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
