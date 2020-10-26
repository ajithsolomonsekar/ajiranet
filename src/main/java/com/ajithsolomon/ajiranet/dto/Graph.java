package com.ajithsolomon.ajiranet.dto;

import java.util.List;

public class Graph {
	private final List<ConnectionDTO> connections;

	public Graph(List<ConnectionDTO> connections) {
		this.connections = connections;
	}

	public List<ConnectionDTO> getEdges() {
		return connections;
	}
}
