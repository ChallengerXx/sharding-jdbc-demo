package com.xx.dao.mapper;

import com.xx.dao.entity.Task;

public interface TaskMapper {

    Task findByTaskNo(String taskNo);

    void insert(Task task);
}
