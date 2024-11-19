package com.example.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.iot.entity.DeviceGroup;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, String> {
   
}
