package com.fizalise.taskmngr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(name = "task_id")
    private UUID taskId;
    @Column(name = "label", nullable = false)
    private String label;
    private String description;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private User author;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tasks_executors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Collection<User> executorList;
}
