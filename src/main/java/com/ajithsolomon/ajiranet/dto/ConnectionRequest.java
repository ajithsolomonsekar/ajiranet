package com.ajithsolomon.ajiranet.dto;

import java.util.List;

public class ConnectionRequest {

	private String source;
	private List<String> targets;

	public ConnectionRequest() {
	}

	public ConnectionRequest(String source, List<String> targets) {
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

	public List<String> getTargets() {
		return targets;
	}

	public void setTargets(List<String> targets) {
		this.targets = targets;
	}

	@Override
	public String toString() {
		return "ConnectionsRequest [source=" + source + ", targets=" + targets + "]";
	}

}
