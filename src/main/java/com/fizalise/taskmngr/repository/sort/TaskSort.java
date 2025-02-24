package com.fizalise.taskmngr.repository.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum TaskSort {
    DATE_DESC(Sort.by(Sort.Direction.DESC, "creationDate"));
    private final Sort sort;
}
