package com.ajithsolomon.ajiranet.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ajithsolomon.ajiranet.constants.AppConstants;
import com.ajithsolomon.ajiranet.dto.ConnectionRequest;
import com.ajithsolomon.ajiranet.dto.ResponseObject;
import com.ajithsolomon.ajiranet.entity.Device;
import com.ajithsolomon.ajiranet.service.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author AjithSolomonSekar
 *
 */
@RestController
@RequestMapping("/ajiranet/process")
public class ResourceController {

	@Autowired
	ResourceService resourceService;

	private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JsonProcessingException
	 */
	@PostMapping
	public ResponseEntity<ResponseObject> create(@RequestBody(required = false) String request)
			throws UnsupportedEncodingException, JsonProcessingException {
		String[] requestArray = URLDecoder.decode(request, StandardCharsets.UTF_8.toString()).split("\\n");
		String[] commandArray = requestArray[0].split("\\s");
		String command = commandArray[0];
		String endPoint = commandArray[1];
		if (requestArray.length > 2) {
			String requestBody = requestArray[2];
			if (command.equals(AppConstants.COMMAND_CREATE.getValue())) {
				if (endPoint.equals(AppConstants.ENDPOINT_001.getValue())) {
					Device device = new ObjectMapper().readValue(requestBody, Device.class);
					logger.info("Creating new " + device.getType() + " with name " + device.getName());
					return resourceService.createDevices(device);
				} else if (endPoint.equals(AppConstants.ENDPOINT_002.getValue())) {
					ConnectionRequest conReq = new ObjectMapper().readValue(requestBody, ConnectionRequest.class);
					if (conReq == null || conReq.getSource() == null || conReq.getTargets() == null) {
						return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_001.getValue()),
								HttpStatus.BAD_REQUEST);
					}
					return resourceService.createConnection(conReq);
				}
			} else if (command.equals(AppConstants.COMMAND_MODIFY.getValue())) {
				try {
					Device device = new ObjectMapper().readValue(requestBody, Device.class);
					String[] endPointArray = endPoint.split("/");
					device.setName(endPointArray[2]);
					return resourceService.modifyStrength(device);
				} catch (Exception e) {
					logger.warn(AppConstants.ERR_003.getValue());
					return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_003.getValue()),
							HttpStatus.BAD_REQUEST);
				}
			}
		} else if (requestArray.length == 1) {
			if (command.equals(AppConstants.COMMAND_FETCH.getValue())) {
				if (endPoint.equals(AppConstants.ENDPOINT_003.getValue())) {
					logger.info(AppConstants.INFO_006.getValue());
					return resourceService.fetchAllDevices();
				} else {
					String[] endPointArray = endPoint.split("[=&]");
					try {
						String source = endPointArray[1];
						String target = endPointArray[3];
						return resourceService.fetchRoutes(source, target);
					} catch (ArrayIndexOutOfBoundsException ex) {
						logger.warn(AppConstants.ERR_008.getValue());
						return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_008.getValue()),
								HttpStatus.BAD_REQUEST);
					}
				}
			} else if (command.equals(AppConstants.COMMAND_CREATE.getValue())) {
				if (endPoint.equals(AppConstants.ENDPOINT_001.getValue())) {
					logger.warn(AppConstants.ERR_002.getValue());
					return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_002.getValue()),
							HttpStatus.BAD_REQUEST);
				} else if (endPoint.equals(AppConstants.ENDPOINT_002.getValue())) {
					logger.warn(AppConstants.ERR_001.getValue());
					return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_001.getValue()),
							HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<>(new ResponseObject(AppConstants.ERR_006.getValue()), HttpStatus.BAD_REQUEST);
	}
}
