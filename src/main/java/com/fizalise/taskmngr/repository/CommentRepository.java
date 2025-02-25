package com.fizalise.taskmngr.repository;

import com.fizalise.taskmngr.entity.Comment;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    int PAGE_SIZE = 2;
    boolean existsByCommentIdAndUser(UUID commentId, User user);
    Page<Comment> findAllByUser(User user, Pageable pageable);
    Page<Comment> findAllByTask(Task task, Pageable pageable);
}
