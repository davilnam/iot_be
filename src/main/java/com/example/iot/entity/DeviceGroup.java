package com.example.iot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class DeviceGroup {
    @Id
    @Column(unique = true)
    String groupId;
    @Column(nullable = false)
    String groupName;
    String description;
}
// group_id (PK): Mã định danh nhóm.
// group_name: Tên nhóm.
// description: Mô tả nhóm.