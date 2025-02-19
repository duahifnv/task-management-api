package com.fizalise.taskmngr.dto;

import com.fizalise.taskmngr.entity.Priority;

import java.util.List;

public record TaskRequest(String label, String description, Priority priority,
                          List<String> executorEmailList) {
}
