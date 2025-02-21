package com.fizalise.taskmngr.mapper;

import com.fizalise.taskmngr.dto.task.TaskRequest;
import com.fizalise.taskmngr.dto.task.TaskResponse;
import com.fizalise.taskmngr.entity.Status;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import com.fizalise.taskmngr.exception.UserNotFoundException;
import com.fizalise.taskmngr.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, Status.class, LocalDate.class})
public abstract class TaskMapper {
    @Autowired
    UserRepository userRepository;
    @Mapping(source = "author", target = "authorId", qualifiedByName = "getUserId")
    public abstract TaskResponse toResponse(Task task);
    public abstract List<TaskResponse> toResponses(List<Task> tasks);
    @Mapping(target = "taskId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "status", expression = "java(Status.PENDING)")
    @Mapping(target = "creationDate", expression = "java(Date.valueOf(LocalDate.now()))")
    @Mapping(source = "taskRequest.executorEmailList", target = "executorList", qualifiedByName = "getExecutorList")
    public abstract Task toTask(TaskRequest taskRequest, User author);
    @Mapping(source = "taskRequest.label", target = "label")
    @Mapping(source = "taskRequest.description", target = "description")
    @Mapping(source = "taskRequest.priority", target = "priority")
    @Mapping(source = "taskRequest.executorEmailList", target = "executorList", qualifiedByName = "getExecutorList")
    public abstract Task toTask(Task task, TaskRequest taskRequest);
    @Named("getUserId")
    public UUID getUserId(User user) {
        return user.getUserId();
    }
    @Named("getExecutorList")
    public List<User> getExecutorList(List<String> emailList) {
        return emailList.stream()
                .map(email -> userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(email)))
                .toList();
    }
}
