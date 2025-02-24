package com.fizalise.taskmngr.repository;

import com.fizalise.taskmngr.entity.Comment;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    boolean existsByCommentIdAndUser(UUID commentId, User user);
    List<Comment> findAllByUser(User user, Sort sort);
    List<Comment> findAllByTask(Task task, Sort sort);
}
