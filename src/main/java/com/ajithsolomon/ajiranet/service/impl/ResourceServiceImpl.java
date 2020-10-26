package com.ajithsolomon.ajiranet.service.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ajithsolomon.ajiranet.constants.AppConstants;
import com.ajithsolomon.ajiranet.constants.DeviceType;
import com.ajithsolomon.ajiranet.dto.ConnectionDTO;
import com.ajithsolomon.ajiranet.dto.ConnectionRequest;
import com.ajithsolomon.ajiranet.dto.DeviceDTO;
import com.ajithsolomon.ajiranet.dto.Graph;
import com.ajithsolomon.ajiranet.dto.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Connection;
import com.ajithsolomon.ajiranet.entity.Device;
import com.ajithsolomon.ajiranet.repository.ConnectionRepository;
import com.ajithsolomon.ajiranet.repository.DeviceRepository;
import com.ajithsolomon.ajiranet.service.ResourceService;

/**
 * 
 * @author AjithSolomonSekar
 *
 */
@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private ConnectionRepository connectionRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

	@Override
	public ResponseEntity<ResponseObject> createDevices(Device device) {
		ResponseObject response = new ResponseObject();
		if (device.getType().equals(DeviceType.REPEATER.getValue())
				|| device.getType().equals(DeviceType.COMPUTER.getValue())) {
			if (!deviceRepository.findById(device.getName()).isPresent()) {
				if (device.getType().equals(DeviceType.COMPUTER.getValue())) {
					device.setValue(5);
				}
				deviceRepository.save(device);
				response.setMsg(AppConstants.INFO_002.getValue() + device.getName());
				logger.info(response.getMsg());
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMsg("Device '" + device.getName() + "' already exists");
			logger.info(response.getMsg());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setMsg("type '" + device.getType() + "' is not supported");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<ResponseObject> modifyStrength(Device dev) {
		ResponseObject response = new ResponseObject();
		Optional<Device> deviceFromDb = deviceRepository.findById(dev.getName());
		if (deviceFromDb.isPresent() && deviceFromDb != null) {
			Device device = deviceFromDb.get();
			if (!device.getType().equals(DeviceType.REPEATER.getValue())) {
				if (dev.getValue() > 0) {
					device.setValue(dev.getValue());
					deviceRepository.save(device);
					response.setMsg(AppConstants.INFO_003.getValue());
					logger.info(response.getMsg());
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					response.setMsg(AppConstants.ERR_010.getValue());
					logger.warn(response.getMsg());
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
			response.setMsg(AppConstants.ERR_007.getValue());
			logger.warn(response.getMsg());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMsg(AppConstants.ERR_004.getValue());
		logger.error(response.getMsg());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<ResponseObject> createConnection(ConnectionRequest conReq) {
		ResponseObject response = new ResponseObject();
		List<Connection> conList = connectionRepository.findAll();
		for (Connection connection : conList) {
			for (String target : conReq.getTargets()) {
				if (connection.getSource().equals(conReq.getSource()) && connection.getTargets().equals(target)) {
					response.setMsg(AppConstants.INFO_004.getValue());
					logger.warn(response.getMsg());
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		}
		Optional<Device> sourcedevice = deviceRepository.findById(conReq.getSource());
		for (String target : conReq.getTargets()) {
			if (!sourcedevice.isPresent()) {
				response.setMsg("Node '" + conReq.getSource() + "' not found");
				logger.warn(response.getMsg());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else if (conReq.getSource().equals(target)) {
				response.setMsg(AppConstants.ERR_005.getValue());
				logger.warn(response.getMsg());
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else if (!deviceRepository.findById(target).isPresent()) {
				response.setMsg("Node '" + target + "' not found");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			connectionRepository.save(new Connection(conReq.getSource(), target));
		}
		response.setMsg(AppConstants.INFO_005.getValue());
		logger.info(response.getMsg());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseObject> fetchAllDevices() {
		ResponseObject response = new ResponseObject();
		List<Device> devices = deviceRepository.findAll();
		if (!CollectionUtils.isEmpty(devices)) {
			response.setDevices(devices);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseObject> fetchRoutes(String source, String target) {
		ResponseObject response = new ResponseObject();
		Optional<Device> sourceDevice = deviceRepository.findById(source);
		Optional<Device> targetDevice = deviceRepository.findById(target);
		if (sourceDevice.isPresent()) {
			if (targetDevice.isPresent()) {
				if (source.equals(target)) {
					response.setMsg("Route is " + source + "->" + target);
					logger.info(response.getMsg());
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				if (!(sourceDevice.get().getType().equals(DeviceType.COMPUTER.getValue())
						&& targetDevice.get().getType().equals(DeviceType.COMPUTER.getValue()))) {
					response.setMsg(AppConstants.ERR_009.getValue());
					logger.warn(response.getMsg());
					return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}

				return new ResponseEntity<>(getPath(toDeviceDTO(sourceDevice.get()), toDeviceDTO(targetDevice.get())),
						HttpStatus.OK);
			}
			response.setMsg("Node '" + target + "' not found");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMsg("Node '" + source + "' not found");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return ResponseObject
	 */
	private ResponseObject getPath(DeviceDTO source, DeviceDTO target) {
		ResponseObject response = new ResponseObject();
		List<Connection> connections = connectionRepository.findAll();
		List<ConnectionDTO> connectionsList = connections.stream().map(c -> toConnectionDTO(c))
				.collect(Collectors.toList());
		PathFinder algo = new PathFinder(new Graph(connectionsList));
		algo.execute(source.getName());
		LinkedList<String> resultPath = algo.getPath(target.getName());
		if (resultPath != null) {
			System.out.println(resultPath);
			List<Device> filteredDevices = deviceRepository.findAll().stream()
					.filter(d -> d.getType().equals(DeviceType.REPEATER.getValue())).collect(Collectors.toList());
			Boolean containsRepeater = false;
			for (Device d : filteredDevices) {
				if (resultPath.contains(d.getName())) {
					containsRepeater = true;
				}
			}
			if (!containsRepeater) {
				if (source.getStrength() >= resultPath.size() - 1) {
					Collections.reverse(resultPath);
					StringBuilder builder = new StringBuilder("Route is " + resultPath.get(0));
					for (int i = 1; i < resultPath.size(); i++) {
						builder.append("->");
						builder.append(resultPath.get(i));
					}
					response.setMsg(builder.toString());
					logger.info(response.getMsg());
					return response;
				}
			} else {
				int strength = 0;
				List<Device> devices = deviceRepository.findAll();
				for (String r : resultPath) {
					Optional<Device> d = devices.stream().filter(dev -> dev.getName().equals(r)).findFirst();
					if (d.get().getType().equals(DeviceType.COMPUTER.getValue())) {
						strength++;
					} else if (d.get().getType().equals(DeviceType.REPEATER.getValue())) {
						strength = (strength / 2) + 1;
					}
				}
				if (source.getStrength() >= strength) {
					Collections.reverse(resultPath);
					StringBuilder builder = new StringBuilder("Route is " + resultPath.get(0));
					for (int i = 1; i < resultPath.size(); i++) {
						builder.append("->");
						builder.append(resultPath.get(i));
					}
					response.setMsg(builder.toString());
					return response;
				}
			}
			response.setMsg("Couldn't reach Node " + target.getName());
			logger.error(response.getMsg());
			return response;
		}
		response.setMsg("Route not found");
		logger.error(response.getMsg());
		return response;
	}

	private DeviceDTO toDeviceDTO(Device d) {
		return new DeviceDTO(d.getName(), d.getType(), d.getValue());
	}

	private ConnectionDTO toConnectionDTO(Connection c) {
		return new ConnectionDTO(c.getId(), c.getSource(), c.getTargets());
	}

}
