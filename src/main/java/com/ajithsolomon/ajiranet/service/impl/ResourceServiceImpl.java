package com.ajithsolomon.ajiranet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ajithsolomon.ajiranet.ResponseObject;
import com.ajithsolomon.ajiranet.constants.DeviceType;
import com.ajithsolomon.ajiranet.entity.Devices;
import com.ajithsolomon.ajiranet.repository.DeviceRepository;
import com.ajithsolomon.ajiranet.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private DeviceRepository deviceRepository;

	public ResponseEntity<ResponseObject> createDevices(Devices device) {
		ResponseObject response = new ResponseObject();

		if (deviceRepository.findById(device.getName()).isPresent()) {
			response.setMsg("Device " + device.getName() + " already exists");
			return new ResponseEntity<ResponseObject> (response, HttpStatus.BAD_REQUEST);
		}
		if (device.getType().equals(DeviceType.COMPUTER.getValue())
				|| device.getType().equals(DeviceType.REPEATER.getValue())) {
			deviceRepository.save(device);
			response.setMsg("Successfully added " + device.getName());
			return new ResponseEntity<ResponseObject> (response, HttpStatus.OK);
		}
		response.setMsg("type " + device.getType() + " is not supported");
		return new ResponseEntity<ResponseObject> (response, HttpStatus.BAD_REQUEST);
	}

}
