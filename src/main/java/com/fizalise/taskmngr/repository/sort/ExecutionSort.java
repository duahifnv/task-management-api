package com.fizalise.taskmngr.repository.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum ExecutionSort {
    START_DESC(Sort.by(Sort.Direction.DESC, "executionStart"));
    private final Sort sort;
}
