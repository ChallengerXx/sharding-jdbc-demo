package controller;

import dao.entity.Task;
import dao.entity.User;
import dao.mapper.TaskMapper;
import dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class HelloController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping("/")
    public String hello(){
//        User userById = userMapper.getUserById("7");
//        System.out.println(userById);
        for (int i = 0; i < 5; i++) {
            String uuid = UUID.randomUUID().toString();
            Task task = taskMapper.findByTaskNo(uuid);
            System.out.println(task);
        }
        return "success";
    }
}
