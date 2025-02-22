package com.fizalise.taskmngr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @Column(name = "comment_id")
    private UUID commentId;
    private String label;
    @Column(name = "message", nullable = false)
    private String message;
    @JoinColumn(name = "task_id", nullable = false)
    @ManyToOne
    private Task task;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User user;
    @Column(name = "creation_time", nullable = false)
    private Timestamp creationTime;
}
