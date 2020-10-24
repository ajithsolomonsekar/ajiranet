package com.ajithsolomon.ajiranet.dto;

public class DeviceDTO {

	private String name;
	private String type;
	private int strength;

	public DeviceDTO(String name, String type, int strength) {
		super();
		this.name = name;
		this.type = type;
		this.strength = strength;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getStrength() {
		return strength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + strength;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceDTO other = (DeviceDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (strength != other.strength)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeviceDTO [name=" + name + ", type=" + type + ", strength=" + strength + "]";
	}

}
