package com.xx.service;

import com.xx.dao.entity.Task;
import com.xx.dao.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Transactional(rollbackFor = Exception.class)
    public void insertList(List<Task> taskList){
        for (Task task : taskList) {
            taskMapper.insert(task);
        }
    }
}
