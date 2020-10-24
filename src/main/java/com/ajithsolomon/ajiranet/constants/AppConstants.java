package com.ajithsolomon.ajiranet.constants;

public enum AppConstants {

	ERR_001("Invalid command syntax"), ERR_002("Invalid Command."), ERR_003("value should be an integer"),
	ERR_004("Device Not Found"), ERR_005("Cannot connect device to itself"), ERR_006("Invalid Syntax - Ajith"),
	ERR_007("Strength cannot be defined for a repeater"), ERR_008("Invalid Request"),
	ERR_009("Route cannot be calculated with repeater"), ERR_010("value should not be negative"),
	COMMAND_CREATE("CREATE"), COMMAND_MODIFY("MODIFY"), COMMAND_FETCH("FETCH"), ENDPOINT_001("/devices"),
	ENDPOINT_002("/connections"), ENDPOINT_003("/devices="), INFO_001("Device already exists"),
	INFO_002("Successfully added "), INFO_003("Successfully defined strength"),
	INFO_004("Devices are already connected"), INFO_005("Successfully connected"), INFO_006("Fetching all devices");

	private String value;

	private AppConstants(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
