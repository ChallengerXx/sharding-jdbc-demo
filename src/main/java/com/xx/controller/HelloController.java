package com.xx.controller;

import com.xx.annotation.MethodDesc;
import com.xx.dao.entity.Task;
import com.xx.dao.mapper.TaskMapper;
import com.xx.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping("/")
    public String hello(){
        return "success";
    }

    @MethodDesc("根据任务号查询任务记录")
    @RequestMapping("/findTaskByTaskNo")
    public Task findTaskByTaskNo(@RequestBody Task request){
        Task task = taskMapper.findByTaskNo(request.getTaskNo());
        return task;
    }

    @RequestMapping("/insertTask")
    public String insertTask(@RequestBody Task task){
        taskMapper.insert(task);
        return "success";
    }
}
