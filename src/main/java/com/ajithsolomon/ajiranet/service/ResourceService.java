package com.ajithsolomon.ajiranet.service;

import org.springframework.http.ResponseEntity;

import com.ajithsolomon.ajiranet.dto.ConnectionRequest;
import com.ajithsolomon.ajiranet.dto.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Device;

public interface ResourceService {

	ResponseEntity<ResponseObject> createDevices(Device device);
	
	ResponseEntity<ResponseObject> modifyStrength(Device device);
	
	ResponseEntity<ResponseObject> createConnection(ConnectionRequest conReq);
	
	ResponseEntity<ResponseObject> fetchAllDevices();
	
	ResponseEntity<ResponseObject> fetchRoutes(String source, String target);
	
}
