package com.example.iot.mapper;

import org.mapstruct.Mapper;

import com.example.iot.dto.request.DeviceCreateRequest;
import com.example.iot.dto.response.DeviceResponse;
import com.example.iot.entity.Device;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Device toDevice(DeviceCreateRequest request);

    DeviceResponse toDeviceResponse(Device device);
}
