package com.ajithsolomon.ajiranet.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONNECTION")
public class Connections implements Serializable {

	private static final long serialVersionUID = -164119849854334935L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "connection_id")
	private Long id;

	@Column(name = "source")
	private String source;

	@Column(name = "targets")
	private String targets;

	public Connections() {
	}

	public Connections(String source, String targets) {
		super();
		this.source = source;
		this.targets = targets;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((targets == null) ? 0 : targets.hashCode());
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
		Connections other = (Connections) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (targets == null) {
			if (other.targets != null)
				return false;
		} else if (!targets.equals(other.targets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Connections [id=" + id + ", source=" + source + ", targets=" + targets + "]";
	}

}
