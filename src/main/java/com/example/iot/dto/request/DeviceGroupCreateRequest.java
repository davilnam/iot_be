package com.example.iot.dto.request;

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
public class DeviceGroupCreateRequest {
    @NotBlank(message = "group id is required")
    String groupId;
    @NotBlank(message = "group id is required")
    String groupName;
    String description;
}
