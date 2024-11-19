package com.example.iot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.example.iot.configuration.MqttSubscriberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WebsocketController {
    
    private final MqttSubscriberService mqttSubscriberService;

    @MessageMapping("/system-toggle")
    public void toggleSystemStatus(StatusRequest request) {
        log.info(request.getStatus());
        // Logic xử lý thay đổi trạng thái của hệ thống
        String newStatus = request.getStatus();
        
        mqttSubscriberService.controlSystem(newStatus);
    }

    @MessageMapping("/set-alarm-time")
    public void toggleSystemStatus(DelayRequest request) {
        log.info(request.getDelay() + "");
        // Logic xử lý thay đổi trạng thái của hệ thống
        // String newStatus = request.getStatus();
        
        mqttSubscriberService.setAlarmDuration(request.getDelay());
    }

    // Dữ liệu request nhận từ client
    public static class StatusRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    // Dữ liệu request nhận từ client
    public static class DelayRequest {
        private int delay;

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }
    }

}
