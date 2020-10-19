package com.ajithsolomon.ajiranet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
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

	public ResponseObject createDevices(Devices device) {
		ResponseObject response = new ResponseObject();
		System.out.println(device);
		if (device.getType().equals(DeviceType.COMPUTER.getValue())
				|| device.getType().equals(DeviceType.REPEATER.getValue())) {
			deviceRepository.save(device);
			response.setMsg("Successfully added " + device.getName());
			return response;
		}
		response.setMsg("type " + device.getType() + " is not supported");
		return response;
	}

}
