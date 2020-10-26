package com.ajithsolomon.ajiranet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.ajithsolomon.ajiranet.dto.ConnectionRequest;
import com.ajithsolomon.ajiranet.entity.Connection;
import com.ajithsolomon.ajiranet.entity.Device;
import com.ajithsolomon.ajiranet.repository.ConnectionRepository;
import com.ajithsolomon.ajiranet.repository.DeviceRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResourceServiceImplTest {

	@InjectMocks
	private ResourceServiceImpl resourceServiceImpl;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private ConnectionRepository connectionRepository;

	@Test
	public void createDevicesTest() {

		Device d1 = new Device("C1", "COMPUTER", 5);
		Device d2 = new Device("R1", "REPEATER", 0);
		Device d3 = new Device("R2", "REPEATER", 0);
		Device d4 = new Device("C1", "PHONE", 5);

		Mockito.when(deviceRepository.findById(d2.getName())).thenReturn(Optional.of(d2));

		assertEquals(HttpStatus.OK, resourceServiceImpl.createDevices(d1).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createDevices(d2).getStatusCode());
		assertEquals(HttpStatus.OK, resourceServiceImpl.createDevices(d3).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createDevices(d4).getStatusCode());

	}

	@Test
	public void modifyStrengthTest() {
		Device d1 = new Device("C1", "COMPUTER", 5);
		Device d2 = new Device("C2", "COMPUTER", 0);
		Device d3 = new Device("R1", "REPEATER", 0);
		Device d4 = new Device("C3", "COMPUTER", 0);

		Mockito.when(deviceRepository.findById(d1.getName())).thenReturn(Optional.of(d1));
		Mockito.when(deviceRepository.findById(d2.getName())).thenReturn(Optional.of(d2));
		Mockito.when(deviceRepository.findById(d3.getName())).thenReturn(Optional.of(d3));

		assertEquals(HttpStatus.OK, resourceServiceImpl.modifyStrength(d1).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.modifyStrength(d2).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.modifyStrength(d3).getStatusCode());
		assertEquals(HttpStatus.NOT_FOUND, resourceServiceImpl.modifyStrength(d4).getStatusCode());
	}

	@Test
	public void createConnectionTest1() {
		List<String> stringList1 = new ArrayList<>();
		stringList1.add("C2");
		List<String> stringList2 = new ArrayList<>();
		stringList2.add("C2");
		List<String> stringList3 = new ArrayList<>();
		stringList3.add("C4");

		ConnectionRequest conReq1 = new ConnectionRequest("C1", stringList1);
		ConnectionRequest conReq2 = new ConnectionRequest("C2", stringList2);
		ConnectionRequest conReq3 = new ConnectionRequest("C3", stringList3);
		ConnectionRequest conReq4 = new ConnectionRequest("C4", stringList2);

		Mockito.when(connectionRepository.findAll()).thenReturn(new ArrayList<>());

		Mockito.when(deviceRepository.findById(conReq1.getSource()))
				.thenReturn(Optional.of(new Device("C1", "COMPUTER", 5)));
		Mockito.when(deviceRepository.findById("C2")).thenReturn(Optional.of(new Device("C2", "COMPUTER", 5)));
		assertEquals(HttpStatus.OK, resourceServiceImpl.createConnection(conReq1).getStatusCode());

		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq2).getStatusCode());

		Mockito.when(deviceRepository.findById(conReq3.getSource())).thenReturn(Optional.ofNullable(null));
		Mockito.when(deviceRepository.findById("C4")).thenReturn(Optional.ofNullable(null));
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq3).getStatusCode());

		Mockito.when(deviceRepository.findById(conReq4.getSource()))
				.thenReturn(Optional.of(new Device("C4", "COMPUTER", 5)));
		Mockito.when(deviceRepository.findById("C2")).thenReturn(Optional.ofNullable(null));
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq4).getStatusCode());
	}

	@Test
	public void createConnectionTest2() {
		List<Connection> connectionList = new ArrayList<>();
		connectionList.add(new Connection("C1", "C2"));

		List<String> stringList1 = new ArrayList<>();
		stringList1.add("C2");

		ConnectionRequest conReq1 = new ConnectionRequest("C1", stringList1);

		Mockito.when(connectionRepository.findAll()).thenReturn(connectionList);

		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq1).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq1).getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.createConnection(conReq1).getStatusCode());

	}

	@Test
	public void fetchAllDevicesNullTest() {
		Mockito.when(deviceRepository.findAll()).thenReturn(new ArrayList<Device>());
		assertEquals(HttpStatus.OK, resourceServiceImpl.fetchAllDevices().getStatusCode());

	}

	@Test
	public void fetchAllDevicesTest() {
		Device d = new Device("C1", "COMPUTER", 5);
		List<Device> deviceList = new ArrayList<>();
		deviceList.add(d);
		Mockito.when(deviceRepository.findAll()).thenReturn(deviceList);
		assertEquals(HttpStatus.OK, resourceServiceImpl.fetchAllDevices().getStatusCode());

	}
	
	@Test
	public void fetchRoutesTest() {
		List<Connection> connectionList = new ArrayList<>();
		connectionList.add(new Connection("C1", "C2"));
		connectionList.add(new Connection("C2", "C4"));
		connectionList.add(new Connection("C2", "R1"));
		connectionList.add(new Connection("R1", "C3"));
		
		Mockito.when(connectionRepository.findAll()).thenReturn(connectionList);
		Mockito.when(deviceRepository.findById("C1")).thenReturn(Optional.of(new Device("C1", "COMPUTER", 5)));
		Mockito.when(deviceRepository.findById("C2")).thenReturn(Optional.ofNullable(null));
		Mockito.when(deviceRepository.findById("C3")).thenReturn(Optional.of(new Device("C3", "COMPUTER", 5)));
		Mockito.when(deviceRepository.findById("R1")).thenReturn(Optional.of(new Device("R1", "REPEATER", 0)));
		Mockito.when(deviceRepository.findById("C4")).thenReturn(Optional.of(new Device("C4", "COMPUTER", 5)));
		
		assertEquals(HttpStatus.OK, resourceServiceImpl.fetchRoutes("C1", "C1").getStatusCode());
		assertEquals(HttpStatus.OK, resourceServiceImpl.fetchRoutes("C1", "C3").getStatusCode());
		assertEquals(HttpStatus.OK, resourceServiceImpl.fetchRoutes("C1", "C4").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.fetchRoutes("C1", "C2").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.fetchRoutes("C2", "C4").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.fetchRoutes("C1", "R1").getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, resourceServiceImpl.fetchRoutes("R1", "C1").getStatusCode());
		
	}
	

}
