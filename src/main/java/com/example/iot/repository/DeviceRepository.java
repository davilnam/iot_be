package com.example.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.iot.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    
}
