package com.ajithsolomon.ajiranet.service;

import org.springframework.http.ResponseEntity;

import com.ajithsolomon.ajiranet.ConnectionsRequest;
import com.ajithsolomon.ajiranet.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Devices;

public interface ResourceService {

	ResponseEntity<ResponseObject> createDevices(Devices device);
	
	ResponseEntity<ResponseObject> modifyStrength(String name, Devices device);
	
	ResponseEntity<ResponseObject> createConnection(ConnectionsRequest conReq);
	
}
