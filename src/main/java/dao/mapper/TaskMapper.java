package dao.mapper;

import dao.entity.Task;

public interface TaskMapper {

    Task findByTaskNo(String taskNo);
}
