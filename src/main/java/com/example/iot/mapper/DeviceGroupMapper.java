package com.example.iot.mapper;

import org.mapstruct.Mapper;

import com.example.iot.dto.request.DeviceGroupCreateRequest;
import com.example.iot.dto.response.DeviceGroupResponse;
import com.example.iot.entity.DeviceGroup;

@Mapper(componentModel = "spring")
public interface DeviceGroupMapper {
    DeviceGroup toDeviceGroup(DeviceGroupCreateRequest request);

    DeviceGroupResponse toDeviceGroupResponse(DeviceGroup deviceGroup);
}
