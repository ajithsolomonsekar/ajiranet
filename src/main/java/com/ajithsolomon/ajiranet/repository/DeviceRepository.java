package com.ajithsolomon.ajiranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajithsolomon.ajiranet.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

}
