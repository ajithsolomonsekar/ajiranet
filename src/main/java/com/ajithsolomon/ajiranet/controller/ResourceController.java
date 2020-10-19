package com.ajithsolomon.ajiranet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajithsolomon.ajiranet.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Devices;
import com.ajithsolomon.ajiranet.service.ResourceService;

@RestController
@RequestMapping("/ajiranet/process")
public class ResourceController {

	@Autowired
	ResourceService resourceService;

	/*@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> createDevices(@RequestBody Device device) {
		System.out.println("In controller");
		return new ResponseEntity<String>(			
				"msg : " + resourceService.createDevices(device), HttpStatus.OK);
	}*/
	
	@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	public ResponseObject createDevices(@RequestBody Devices device) {
		System.out.println("In controller");
		return resourceService.createDevices(device);
	}

}
