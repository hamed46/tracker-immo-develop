package com.trackerimmo.model;

public enum AdsType {
	buy("buy"), rent("rent");

	private String code;

	private AdsType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static AdsType of(String code) {
		for (AdsType e : values()) {
			if (e.code.equals(code)) {
				return e;
			}
		}
		return null;
	}
}
