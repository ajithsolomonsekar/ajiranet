package com.ajithsolomon.ajiranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajithsolomon.ajiranet.entity.Devices;

@Repository
public interface DeviceRepository extends JpaRepository<Devices, Long> {

}
