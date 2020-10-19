package com.ajithsolomon.ajiranet.service.impl;

import java.util.Optional;

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

		if (device.getType().equals(DeviceType.COMPUTER.getValue())
				|| device.getType().equals(DeviceType.REPEATER.getValue())) {
			if (deviceRepository.findById(device.getName()).isPresent()) {
				response.setMsg("Device '" + device.getName() + "' already exists");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			}
			deviceRepository.save(device);
			response.setMsg("Successfully added " + device.getName());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
		} else {
			response.setMsg("type '" + device.getType() + "' is not supported");
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<ResponseObject> modifyStrength(String name, Devices dev) {
		ResponseObject response = new ResponseObject();
		Optional<Devices> device = deviceRepository.findById(name);
		if (device.isPresent()) {
			if (isNumeric(dev.getValue())) {
				deviceRepository.save(new Devices(name, device.get().getType(), dev.getValue()));
				response.setMsg("Successfully defined strength");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			response.setMsg("value should be an integer");
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMsg("Device Not Found");
		return new ResponseEntity<ResponseObject>(response, HttpStatus.NOT_FOUND);
	}

	private static boolean isNumeric(String strength) {
		if (strength == null) {
			return false;
		}
		try {
			Integer.parseInt(strength);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
