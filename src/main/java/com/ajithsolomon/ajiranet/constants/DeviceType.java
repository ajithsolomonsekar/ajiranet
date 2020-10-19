package com.ajithsolomon.ajiranet.constants;

public enum DeviceType {
	COMPUTER("COMPUTER"), REPEATER("REPEATER");

	private String value;

	private DeviceType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
