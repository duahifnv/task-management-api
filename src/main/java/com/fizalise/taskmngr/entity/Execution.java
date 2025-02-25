package com.fizalise.taskmngr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "tasks_executors")
@IdClass(ExecutionId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Execution {
    @Id
    @Column(name = "task_id")
    private UUID taskId;
    @Id
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "execution_start", nullable = false)
    private Timestamp executionStart;
}
