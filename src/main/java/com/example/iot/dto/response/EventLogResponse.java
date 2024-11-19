package com.example.iot.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class EventLogResponse {
    int eventId;
    String details;
    @JsonFormat(pattern = "HH:mm:ss dd-MM-yyyy")
    LocalDateTime timestamp;
    DeviceResponse deviceResponse;
}
