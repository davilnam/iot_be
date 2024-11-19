package com.example.iot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.iot.dto.request.DeviceCreateRequest;
import com.example.iot.dto.request.DeviceUpdateRequest;
import com.example.iot.dto.response.DeviceGroupResponse;
import com.example.iot.dto.response.DeviceResponse;
import com.example.iot.entity.Device;
import com.example.iot.entity.DeviceGroup;
import com.example.iot.exception.AppException;
import com.example.iot.exception.ErrorCode;
import com.example.iot.mapper.DeviceGroupMapper;
import com.example.iot.mapper.DeviceMapper;
import com.example.iot.repository.DeviceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceService {
    DeviceRepository deviceRepository;
    DeviceMapper deviceMapper;
    DeviceGroupMapper deviceGroupMapper;
    DeviceGroupService deviceGroupService;

    public DeviceResponse createDevice(DeviceCreateRequest request) {
        DeviceGroup deviceGroup = deviceGroupService.getDeviceGroupById(request.getGroupId());

        Device device = deviceMapper.toDevice(request);
        device.setDeviceGroup(deviceGroup);

        deviceRepository.save(device);

        DeviceResponse deviceResponse = deviceMapper.toDeviceResponse(device);
        DeviceGroupResponse deviceGroupResponse = deviceGroupMapper.toDeviceGroupResponse(deviceGroup);
        deviceResponse.setDeviceGroupResponse(deviceGroupResponse);

        return deviceResponse;
    }

    public List<DeviceResponse> getAllDevice() {
        List<Device> listDevices = deviceRepository.findAll();
        List<DeviceResponse> res = listDevices.stream()
                .map(device -> {
                    DeviceResponse deviceResponse = deviceMapper.toDeviceResponse(device);
                    DeviceGroupResponse deviceGroupResponse = deviceGroupMapper.toDeviceGroupResponse(device.getDeviceGroup());
                    deviceResponse.setDeviceGroupResponse(deviceGroupResponse);
                    return deviceResponse;
                }).toList();

        return res;
    }

    public DeviceResponse updateDevice(String id, DeviceUpdateRequest request){
        Device device = deviceRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED));

        if(request.getDeviceName() != null && !device.getDeviceName().equals(request.getDeviceName())){
            device.setDeviceName(request.getDeviceName());
        }
        if(request.getLocation() != null && !device.getLocation().equals(request.getLocation())){
            device.setLocation(request.getLocation());
        }
        if(request.getStatus() != null && !device.getStatus().equals(request.getStatus())){
            device.setStatus(request.getStatus());
        }

        DeviceResponse deviceResponse = deviceMapper.toDeviceResponse(device);
        if(request.getGroupId() != null && !device.getDeviceGroup().getGroupId().equals(request.getGroupId())){
            DeviceGroup deviceGroup = deviceGroupService.getDeviceGroupById(request.getGroupId());

            device.setDeviceGroup(deviceGroup);
            DeviceGroupResponse deviceGroupResponse = deviceGroupMapper.toDeviceGroupResponse(deviceGroup);
            deviceResponse.setDeviceGroupResponse(deviceGroupResponse);
        }else{
            DeviceGroupResponse deviceGroupResponse = deviceGroupMapper.toDeviceGroupResponse(device.getDeviceGroup());
            deviceResponse.setDeviceGroupResponse(deviceGroupResponse);
        }

        deviceRepository.save(device);
        return deviceResponse;
    }

    public void deleteDevice(String id){
        deviceRepository.deleteById(id);
    }
}
