package com.ajithsolomon.ajiranet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ajithsolomon.ajiranet.constants.AppConstants;
import com.ajithsolomon.ajiranet.constants.DeviceType;
import com.ajithsolomon.ajiranet.dto.ConnectionsRequest;
import com.ajithsolomon.ajiranet.dto.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Connections;
import com.ajithsolomon.ajiranet.entity.Devices;
import com.ajithsolomon.ajiranet.repository.ConnectionRepository;
import com.ajithsolomon.ajiranet.repository.DeviceRepository;
import com.ajithsolomon.ajiranet.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ConnectionRepository connectionRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

	public ResponseEntity<ResponseObject> createDevices(Devices device) {
		ResponseObject response = new ResponseObject();

		if (device.getType().equals(DeviceType.COMPUTER.getValue())
				|| device.getType().equals(DeviceType.REPEATER.getValue())) {
			if (deviceRepository.findById(device.getName()).isPresent()) {
				logger.info(AppConstants.INFO_001.getValue());
				response.setMsg("Device '" + device.getName() + "' already exists");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			}
			deviceRepository.save(device);
			logger.info("New " + device + " created");
			response.setMsg(AppConstants.INFO_002.getValue() + device.getName());
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
				logger.info(AppConstants.INFO_003.getValue());
				response.setMsg(AppConstants.INFO_003.getValue());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
			response.setMsg(AppConstants.ERR_003.getValue());
			logger.warn(AppConstants.ERR_003.getValue());
			return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
		}
		logger.error(AppConstants.ERR_004.getValue());
		response.setMsg(AppConstants.ERR_004.getValue());
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

	@Override
	public ResponseEntity<ResponseObject> createConnection(ConnectionsRequest conReq) {
		ResponseObject response = new ResponseObject();

		List<Connections> conList = connectionRepository.findAll();
		for (Connections connection : conList) {
			for (String target : conReq.getTargets()) {
				if (connection.getSource().equals(conReq.getSource()) && connection.getTargets().equals(target)) {
					logger.warn(AppConstants.INFO_004.getValue());
					response.setMsg(AppConstants.INFO_004.getValue());
					return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
				}
			}
		}
		Optional<Devices> sourcedevice = deviceRepository.findById(conReq.getSource());
		List<Connections> connectionList = new ArrayList<>();

		for (String target : conReq.getTargets()) {
			if (sourcedevice.isPresent()) {
				if (conReq.getSource() == target) {
					logger.warn(AppConstants.ERR_005.getValue());
					response.setMsg(AppConstants.ERR_005.getValue());
					return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
				}
				if (deviceRepository.findById(target).isPresent()) {
					Connections connection = new Connections(conReq.getSource(), target);
					connectionList.add(connection);
				} else {
					response.setMsg("Node '" + target + "' not found");
					return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
				}
			} else {
				logger.warn("Node '" + conReq.getSource() + "' not found");
				response.setMsg("Node '" + conReq.getSource() + "' not found");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			}
		}
		sourcedevice.get().setConnections(connectionList);
		deviceRepository.save(sourcedevice.get());
		logger.info(AppConstants.INFO_005.getValue());
		response.setMsg(AppConstants.INFO_005.getValue());
		return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
	}
}
