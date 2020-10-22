package com.ajithsolomon.ajiranet.constants;

public enum AppConstants {

	ERR_001("Invalid command syntax"), ERR_002("Invalid Command."), ERR_003("value should be an integer"),
	ERR_004("Device Not Found"), ERR_005("Cannot connect device to itself"), COMMAND_CREATE("CREATE"),
	COMMAND_MODIFY("MODIFY"), COMMAND_FETCH("FETCH"), ENDPOINT_001("/devices"), ENDPOINT_002("/connections"),
	INFO_001("Device already exists"), INFO_002("Successfully added "), INFO_003("Successfully defined strength"),
	INFO_004("Devices are already connected"), INFO_005("Successfully connected"), INFO_006("Fetching all devices");

	private String value;

	private AppConstants(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
