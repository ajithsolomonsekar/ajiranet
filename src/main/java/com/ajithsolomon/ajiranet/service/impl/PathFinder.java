package com.ajithsolomon.ajiranet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ajithsolomon.ajiranet.dto.ConnectionDTO;
import com.ajithsolomon.ajiranet.dto.Graph;;

public class PathFinder {

	//private final List<DeviceDTO> devices;
	private final List<ConnectionDTO> connections;
	private Set<String> settledDevices;
	private Set<String> unSettledDevices;
	private Map<String, String> predecessors;
	private Map<String, Integer> distance;

	public PathFinder(Graph graph) {
		//this.devices = new ArrayList<DeviceDTO>(graph.getVertexes());
		this.connections = new ArrayList<ConnectionDTO>(graph.getEdges());
	}

	public void execute(String source) {
		settledDevices = new HashSet<String>();
		unSettledDevices = new HashSet<String>();
		distance = new HashMap<String, Integer>();
		predecessors = new HashMap<String, String>();
		distance.put(source, 0);
		unSettledDevices.add(source);
		while (unSettledDevices.size() > 0) {
			String node = getMinimum(unSettledDevices);
			settledDevices.add(node);
			unSettledDevices.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(String DeviceDTO) {
		List<String> adjacentDeviceDTOs = getNeighbors(DeviceDTO);
		for (String target : adjacentDeviceDTOs) {
			if (getShortestDistance(target) > getShortestDistance(DeviceDTO) + 1) {
				distance.put(target, getShortestDistance(DeviceDTO) + 1);
				predecessors.put(target, DeviceDTO);
				unSettledDevices.add(target);
			}
		}
	}

	private List<String> getNeighbors(String device) {
		List<String> neighbors = new ArrayList<String>();
		for (ConnectionDTO edge : connections) {
			if (edge.getSource().equals(device) && !isSettled(edge.getDestination())) {
				neighbors.add(edge.getDestination());
			}
			if (edge.getDestination().equals(device) && !isSettled(edge.getSource())) {
				neighbors.add(edge.getSource());
			}
		}
		return neighbors;
	}

	private String getMinimum(Set<String> unSetDevices) {
		String minimumDeviceDTO = null;
		for (String unsetDev : unSetDevices) {
			if (minimumDeviceDTO == null) {
				minimumDeviceDTO = unsetDev;
			} else {
				if (getShortestDistance(unsetDev) < getShortestDistance(minimumDeviceDTO)) {
					minimumDeviceDTO = unsetDev;
				}
			}
		}
		return minimumDeviceDTO;
	}

	private boolean isSettled(String device) {
		return settledDevices.contains(device);
	}

	private int getShortestDistance(String destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	public LinkedList<String> getPath(String target) {
		LinkedList<String> path = new LinkedList<String>();
		if (predecessors.get(target) == null) {
			return null;
		}
		path.add(target);
		while (predecessors.get(target) != null) {
			target = predecessors.get(target);
			path.add(target);
		}
		// Put it into the correct order
		//Collections.reverse(path);
		return path;
	}

}
