package com.ajithsolomon.ajiranet.dto;

public class ConnectionDTO {
	private final Long id;
	private final String source;
	private final String destination;

	public ConnectionDTO(Long id, String source, String destination) {
		super();
		this.id = id;
		this.source = source;
		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	public String getSource() {
		return source;
	}

	public String getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return "ConnectionDTO [id=" + id + ", source=" + source + ", destination=" + destination + "]";
	}

}
