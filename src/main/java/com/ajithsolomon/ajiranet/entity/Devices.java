package com.ajithsolomon.ajiranet.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_DEVICE")
public class Devices {

	@Id
	@Column(name = "device_name", nullable = false)
	private String name;

	@Column(name = "device_type", nullable = false)
	private String type;

	@Column(name = "value")
	private String value;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "source", referencedColumnName = "device_name")
	List<Connections> connections;

	public Devices() {
	}

	public Devices(String name, String type, String value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public Devices(String name, String type, String value, List<Connections> connections) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
		this.connections = connections;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Connections> getConnections() {
		return connections;
	}

	public void setConnections(List<Connections> connections) {
		this.connections = connections;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connections == null) ? 0 : connections.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (connections == null) {
			if (other.connections != null)
				return false;
		} else if (!connections.equals(other.connections))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Devices [name=" + name + ", type=" + type + ", value=" + value + ", connections=" + connections + "]";
	}

}
