package com.example.iot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.iot.dto.ApiResponse;
import com.example.iot.dto.response.EventLogResponse;
import com.example.iot.services.EventLogService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/event-log")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventLogController {
    EventLogService eventLogService;

    @GetMapping
    public ApiResponse<List<EventLogResponse>> getAllEventLog() {
        return ApiResponse.<List<EventLogResponse>>builder()
                .message("get all event log success")
                .result(eventLogService.getAllEventLog())
                .build();
    }
}
