package com.ajithsolomon.ajiranet.service;

import org.springframework.http.ResponseEntity;

import com.ajithsolomon.ajiranet.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Devices;

public interface ResourceService {

	public ResponseEntity<ResponseObject> createDevices(Devices device);
}
