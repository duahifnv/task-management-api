package com.fizalise.taskmngr.repository.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum UserSort {
    NAME_ASC(Sort.by(Sort.Direction.ASC, "name"));
    private final Sort sort;
}
