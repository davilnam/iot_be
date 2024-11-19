package com.example.iot.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int eventId;
    String details;
    LocalDateTime timestamp;    

    @ManyToOne
    @JoinColumn(name = "device_id", referencedColumnName = "deviceId", nullable = false)
    Device device;
}
// event_id (PK): Mã định danh sự kiện.
// device_id (FK): Mã thiết bị liên quan đến sự kiện.
// timestamp: Thời gian xảy ra sự kiện.
// details: Thông tin chi 	tiết.