package com.example.iot.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.iot.dto.request.MotionEvent;
import com.example.iot.entity.Device;
import com.example.iot.entity.EventLog;
import com.example.iot.exception.AppException;
import com.example.iot.exception.ErrorCode;
import com.example.iot.repository.DeviceRepository;
import com.example.iot.repository.EventLogRepository;
import com.example.iot.services.EmailNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MqttSubscriberService {

    private static final String MQTT_BROKER_URL = "tcp://192.168.180.172:1883";
    private static final String MQTT_TOPIC = "ESP32/Notification";
    private static final String MQTT_CONTROL_TOPIC = "ESP32/Control";
    private static final String MQTT_DURATION_TOPIC = "ESP32/Duration";

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private EventLogRepository eventLogRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        try {
            this.mqttClient = new MqttClient(MQTT_BROKER_URL, MqttClient.generateClientId());
            connectToBroker();
            subscribeToNotification(); // Subscribe sau khi kết nối thành công
        } catch (MqttException e) {
            log.error("Failed to initialize and connect MQTT client: {}", e.getMessage());
        }
    }

    public void connectToBroker() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            mqttClient.connect(options);
            log.info("Connected to MQTT broker: {}", MQTT_BROKER_URL);
        } catch (MqttException e) {
            log.error("Failed to connect to MQTT broker: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        // Start reconnect thread
        startReconnectThread();
    }

    private void startReconnectThread() {
        new Thread(() -> {
            while (true) {
                try {
                    if (!mqttClient.isConnected()) {
                        log.warn("Reconnecting to MQTT broker...");
                        connectToBroker();
                    }
                    Thread.sleep(10000);
                } catch (Exception e) {
                    log.error("Error while trying to reconnect to MQTT: {}", e.getMessage());
                }
            }
        }).start();
    }

    public void subscribeToNotification() {
        try {
            mqttClient.subscribe(MQTT_TOPIC, (topic, message) -> {
                String payload = new String(message.getPayload());
                log.info("Received MQTT message: {}", payload);
                processAndSave(payload);
            });
            log.info("Subscribed to topic: {}", MQTT_TOPIC);
        } catch (MqttException e) {
            log.error("Failed to subscribe to topic {}: {}", MQTT_TOPIC, e.getMessage());
        }
    }

    private void processAndSave(String payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MotionEvent motionEvent = objectMapper.readValue(payload, MotionEvent.class);
            log.info("Parsed Event: {}", motionEvent);

            Device device = deviceRepository.findById(motionEvent.getDeviceId())
                    .orElseThrow(() -> new AppException(ErrorCode.DEVICE_NOT_EXISTED));

            LocalDateTime timestamp = LocalDateTime.parse(motionEvent.getTimestamp(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            EventLog eventLog = EventLog.builder()
                    .details(motionEvent.getDetails())
                    .timestamp(timestamp)
                    .device(device)
                    .build();

            eventLogRepository.save(eventLog);
            log.info("Saved event log for device: {}", motionEvent.getDeviceId());
            long cnt = eventLogRepository.countById();

            // Tạo payload mới bao gồm thông tin deviceName và location
            Map<String, Object> extendedPayload = new HashMap<>();
            extendedPayload.put("details", motionEvent.getDetails());
            extendedPayload.put("timestamp", motionEvent.getTimestamp());
            extendedPayload.put("deviceId", motionEvent.getDeviceId());
            extendedPayload.put("deviceName", device.getDeviceName());
            extendedPayload.put("location", device.getLocation());
            extendedPayload.put("cnt", cnt);

            // Chuyển extendedPayload thành JSON
            String updatedPayload = objectMapper.writeValueAsString(extendedPayload);

            // Gửi payload mới qua WebSocket
            messagingTemplate.convertAndSend("/topic/motion-detected", updatedPayload);
            
            emailNotificationService.sendEmail("trannamdz96@gmail.com", "Motion Alert",
                    "Thiết bị: " + device.getDeviceName() + " phát hiện chuyển động bất thường tại "
                            + device.getLocation() + " vui lòng kiểm tra để đảm bảo an toàn");
        } catch (Exception e) {
            log.error("Failed to process and save MQTT message: {}", e.getMessage());
        }
    }

    public void controlSystem(String command) {
        publishMessage(MQTT_CONTROL_TOPIC, command);
    }

    public void setAlarmDuration(int durationInSeconds) {
        publishMessage(MQTT_DURATION_TOPIC, String.valueOf(durationInSeconds));
    }

    private void publishMessage(String topic, String messageContent) {
        try {
            mqttClient.publish(topic, new MqttMessage(messageContent.getBytes()));
            log.info("Published message to topic {}: {}", topic, messageContent);
        } catch (MqttException e) {
            log.error("Failed to publish message to topic {}: {}", topic, e.getMessage());
        }
    }

    @PreDestroy
    public void disconnect() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                log.info("Disconnected from MQTT broker");
            }
        } catch (MqttException e) {
            log.error("Failed to disconnect from MQTT broker: {}", e.getMessage());
        }
    }
}
