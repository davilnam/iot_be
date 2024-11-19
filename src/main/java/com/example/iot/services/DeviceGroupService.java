package com.example.iot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.iot.dto.request.DeviceGroupCreateRequest;
import com.example.iot.dto.request.DeviceGroupUpdateRequest;
import com.example.iot.dto.response.DeviceGroupResponse;
import com.example.iot.entity.DeviceGroup;
import com.example.iot.exception.AppException;
import com.example.iot.exception.ErrorCode;
import com.example.iot.mapper.DeviceGroupMapper;
import com.example.iot.repository.DeviceGroupRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceGroupService {
    DeviceGroupRepository deviceGroupRepository;
    DeviceGroupMapper deviceGroupMapper;

    public DeviceGroupResponse createDeviceGroup(DeviceGroupCreateRequest request) {
        DeviceGroup deviceGroup = deviceGroupMapper.toDeviceGroup(request);
        deviceGroupRepository.save(deviceGroup);
        return deviceGroupMapper.toDeviceGroupResponse(deviceGroup);
    }

    public List<DeviceGroupResponse> getAll() {
        List<DeviceGroup> listDeviceGroups = deviceGroupRepository.findAll();
        List<DeviceGroupResponse> res = listDeviceGroups.stream().map(deviceGroupMapper::toDeviceGroupResponse)
                .toList();
        return res;
    }

    public void deleteDeviceGroup(String id) {
        deviceGroupRepository.deleteById(id);
    }

    public DeviceGroupResponse updateDeviceGroup(String id, DeviceGroupUpdateRequest request) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEVICE_GROUP_NOT_EXISTED));

        if (request.getGroupName() != null && !deviceGroup.getGroupName().equals(request.getGroupName())) {
            deviceGroup.setGroupName(request.getGroupName());
        }
        if (request.getDescription() != null && !deviceGroup.getDescription().equals(request.getDescription())) {
            deviceGroup.setDescription(request.getDescription());
        }

        deviceGroupRepository.save(deviceGroup);
        return deviceGroupMapper.toDeviceGroupResponse(deviceGroup);
    }

    public DeviceGroup getDeviceGroupById(String id) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DEVICE_GROUP_NOT_EXISTED));
        return deviceGroup;
    }
}
