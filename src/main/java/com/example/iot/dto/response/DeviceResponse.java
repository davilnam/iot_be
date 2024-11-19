package com.example.iot.dto.response;

import com.example.iot.enums.Status;

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
public class DeviceResponse {
    String deviceId;
    String deviceName;
    String location;
    Status status;
    DeviceGroupResponse deviceGroupResponse;
}
