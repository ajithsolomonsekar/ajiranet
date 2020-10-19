package com.ajithsolomon.ajiranet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_DEVICE")
public class Devices {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "device_type", nullable = false)
	private String type;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "strength")
	private String strength;

	public Devices() {
	}

	public Devices(String type, String name, String strength) {
		super();
		this.type = type;
		this.name = name;
		this.strength = strength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((strength == null) ? 0 : strength.hashCode());
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
		Devices other = (Devices) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name != other.name)
			return false;
		if (strength != other.strength)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Devices [id=" + id + ", type=" + type + ", name=" + name + ", strength=" + strength + "]";
	}

}
