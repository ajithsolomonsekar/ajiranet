package com.ajithsolomon.ajiranet.service;

import org.springframework.http.ResponseEntity;

import com.ajithsolomon.ajiranet.dto.ConnectionsRequest;
import com.ajithsolomon.ajiranet.dto.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Devices;

public interface ResourceService {

	ResponseEntity<ResponseObject> createDevices(Devices device);
	
	ResponseEntity<ResponseObject> modifyStrength(Devices device);
	
	ResponseEntity<ResponseObject> createConnection(ConnectionsRequest conReq);
	
	ResponseEntity<ResponseObject> fetchAllDevices();
	
}
