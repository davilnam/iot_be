package com.example.iot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.iot.entity.EventLog;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Integer>{
    @Query("SELECT COUNT(e.eventId) FROM EventLog e")
    long countById();
}
