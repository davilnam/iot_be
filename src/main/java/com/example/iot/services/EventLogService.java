package com.example.iot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.iot.dto.response.DeviceResponse;
import com.example.iot.dto.response.EventLogResponse;
import com.example.iot.entity.EventLog;
import com.example.iot.mapper.DeviceMapper;
import com.example.iot.repository.EventLogRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventLogService {
    EventLogRepository eventLogRepository;
    DeviceMapper deviceMapper;

    public List<EventLogResponse> getAllEventLog(){
        List<EventLog> listEventLogs = eventLogRepository.findAll();

        List<EventLogResponse> res = listEventLogs.stream()
        .map(event -> {
            EventLogResponse eventLogResponse = EventLogResponse.builder()
            .eventId(event.getEventId())
            .details(event.getDetails())
            .timestamp(event.getTimestamp())
            .build();

            DeviceResponse deviceResponse = deviceMapper.toDeviceResponse(event.getDevice());

            eventLogResponse.setDeviceResponse(deviceResponse);

            return eventLogResponse;
        }).toList();
        return res;
    }
}
