package com.fizalise.taskmngr.mapper;

import com.fizalise.taskmngr.dto.TaskResponse;
import com.fizalise.taskmngr.entity.Task;
import com.fizalise.taskmngr.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "author", target = "authorId", qualifiedByName = "getUserId")
    TaskResponse toResponse(Task task);
    List<TaskResponse> toResponses(List<Task> tasks);
    @Named("getUserId")
    default UUID getUserId(User user) {
        return user.getUserId();
    }
}
