package com.ajithsolomon.ajiranet.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "TBL_DEVICE")
public class Devices implements Serializable {

	private static final long serialVersionUID = 6675046415021647129L;

	@Column(name = "device_type")
	private String type;

	@Id
	@Column(name = "device_name", nullable = false)
	private String name;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "strength")
	private int value;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "source", referencedColumnName = "device_name")
	List<Connections> connections;

	public Devices() {
	}

	public Devices(String name, String type, int value) {
		super();
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public Devices(String name) {
		super();
		this.name = name;
	}

	public Devices(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}

	public Devices(String name, String type, int value, List<Connections> connections) {
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
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
		result = prime * result + value;
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
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Devices [type=" + type + ", name=" + name + ", value=" + value + ", connections=" + connections + "]";
	}

}
