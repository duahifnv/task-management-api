package com.fizalise.taskmngr.repository.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum CommentSort {
    CREATED_DESC(Sort.by(Sort.Direction.DESC, "creationTime"));
    private final Sort sort;
}
