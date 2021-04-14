package com.xx.service;

import com.xx.dao.entity.Task;
import com.xx.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {

    @Autowired
    TaskService taskService;

    /**
     * 事务测试
     */
    @Test
    public void test1(){
        Task task1 = new Task();
        task1.setResult("success");
        task1.setStatus("1");
        task1.setTaskNo("1");
        Task task2 = new Task();
        task2.setResult("success");
        task2.setStatus("111");
        task2.setTaskNo("2");
        List<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);
        taskService.insertList(list);
    }
}
