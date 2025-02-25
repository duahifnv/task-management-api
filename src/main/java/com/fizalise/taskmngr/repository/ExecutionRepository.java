package com.fizalise.taskmngr.repository;

import com.fizalise.taskmngr.entity.Execution;
import com.fizalise.taskmngr.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExecutionRepository extends JpaRepository<Execution, UUID> {
    int PAGE_SIZE = 2;
    Optional<Execution> findByTaskIdAndUserId(UUID taskId, UUID userId);
    int countByTaskId(UUID taskId);
    int countByTaskIdAndStatusEquals(UUID taskId, Status status);
}
