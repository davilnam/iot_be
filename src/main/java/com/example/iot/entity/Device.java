package com.example.iot.entity;

import com.example.iot.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Device {
    @Id
    @Column(unique = true)
    String deviceId;
    String deviceName;
    String location;
    Status status;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "groupId", nullable = false)
    DeviceGroup deviceGroup;
}
// device_id (PK): Mã định danh thiết bị.
// device_name: Tên thiết bị.
// device_type: Loại thiết bị (sensor, siren, relay).
// location: Vị trí lắp đặt.
// status: Trạng thái hoạt động (online, offline).
