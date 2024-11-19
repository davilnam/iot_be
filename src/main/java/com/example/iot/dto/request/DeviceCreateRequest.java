package com.example.iot.dto.request;

import com.example.iot.enums.Status;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceCreateRequest {
    @NotBlank(message = "device id is required")
    String deviceId;
    @NotBlank(message = "device name is required")
    String deviceName;
    @NotBlank(message = "location is required")
    String location;    
    Status status;
    @NotBlank(message ="device group id is required")
    String groupId;
}
