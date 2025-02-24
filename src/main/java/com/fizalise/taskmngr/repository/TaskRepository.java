package com.fizalise.taskmngr.repository;

import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    int PAGE_SIZE = 2;
    boolean existsByTaskId(UUID taskId);
    @Query("from Task t join t.executorList e where e = :executor")
    Page<Task> findAllByExecutor(@Param("executor") User executor, Pageable pageable);
    @Query("""
            from Task t join t.executorList e
            where (?1 is null or t.author.userId = ?1)
            and (?2 is null or e.userId = ?2)
            """)
    Page<Task> findAllFiltered(UUID authorId, UUID executorId, Pageable pageable);
}
