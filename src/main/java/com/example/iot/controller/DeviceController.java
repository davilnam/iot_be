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
import com.example.iot.dto.request.DeviceCreateRequest;
import com.example.iot.dto.request.DeviceUpdateRequest;
import com.example.iot.dto.response.DeviceResponse;
import com.example.iot.services.DeviceService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/device")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DeviceController {
    DeviceService deviceService;

    @GetMapping
    public ApiResponse<List<DeviceResponse>> getAllDevice() {
        return ApiResponse.<List<DeviceResponse>>builder()
                .message("get all success")
                .result(deviceService.getAllDevice())
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<DeviceResponse> createDevice(@RequestBody DeviceCreateRequest request) {
        return ApiResponse.<DeviceResponse>builder()
                .message("create device success")
                .result(deviceService.createDevice(request))
                .build();

    }

    @PutMapping("/{deviceId}")
    public ApiResponse<DeviceResponse> updateDevice(@PathVariable String deviceId,
            @RequestBody DeviceUpdateRequest request) {
        return ApiResponse.<DeviceResponse>builder()
                .message("update success")
                .result(deviceService.updateDevice(deviceId, request))
                .build();
    }

    @DeleteMapping("/{deviceId}")
    public ApiResponse<Void> deleteDevice(@PathVariable String deviceId) {
        deviceService.deleteDevice(deviceId);
        return ApiResponse.<Void>builder()
                .message("delete device success")
                .build();
    }
}
