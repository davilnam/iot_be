package com.example.iot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iot.dto.ApiResponse;
import com.example.iot.dto.request.DeviceGroupCreateRequest;
import com.example.iot.dto.request.DeviceGroupUpdateRequest;
import com.example.iot.dto.response.DeviceGroupResponse;
import com.example.iot.services.DeviceGroupService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/device-group")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceGroupController {
    DeviceGroupService deviceGroupService;

    @PostMapping("/create")
    public ApiResponse<DeviceGroupResponse> createDeviceGroup(@RequestBody DeviceGroupCreateRequest request) {
        return ApiResponse.<DeviceGroupResponse>builder()
                .message("create success")
                .result(deviceGroupService.createDeviceGroup(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DeviceGroupResponse>> getAllDeviceGroup() {
        return ApiResponse.<List<DeviceGroupResponse>>builder()
                .message("get all success")
                .result(deviceGroupService.getAll())
                .build();
    }

    @DeleteMapping("/{deviceGroupId}")
    public ApiResponse<Void> deleteDeviceGroup(@PathVariable String deviceGroupId) {
        deviceGroupService.deleteDeviceGroup(deviceGroupId);
        return ApiResponse.<Void>builder()
                .message("delete success")
                .build();
    }

    @PutMapping("/{deviceGroupId}")
    public ApiResponse<DeviceGroupResponse> updateDeviceGroup(
            @PathVariable String deviceGroupId,
            @RequestBody DeviceGroupUpdateRequest request) {
        return ApiResponse.<DeviceGroupResponse>builder()
                .message("update success")
                .result(deviceGroupService.updateDeviceGroup(deviceGroupId, request))
                .build();
    }
}
