package com.ajithsolomon.ajiranet.dto;

import java.util.List;


public class Graph {
	private final List<DeviceDTO> devices;
	private final List<ConnectionDTO> connections;

	public Graph(List<ConnectionDTO> connections) {
		this.devices = null;
		this.connections = connections;
	}

	public List<DeviceDTO> getVertexes() {
		return devices;
	}

	public List<ConnectionDTO> getEdges() {
		return connections;
	}
}
