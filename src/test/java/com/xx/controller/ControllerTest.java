package com.xx.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional(transactionManager = "transactionManager")
@Rollback //对数据库的增删改都会回滚,便于测试用例的循环利用
public class ControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        /**
         * MockMvcBuilders提供两方法
         *  public static DefaultMockMvcBuilder webAppContextSetup(WebApplicationContext context) {
         *       return new DefaultMockMvcBuilder(context);
         *   }
         *   public static StandaloneMockMvcBuilder standaloneSetup(Object... controllers) {
         *       return new StandaloneMockMvcBuilder(controllers);
         *   }
         *
         *   通过build()方法构造MockMvc对象
         */
        // 独立安装测试
        // mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
        // 集成web环境测试（这种方式并不会集成真正的web环境，而是通过相应的Mock Api进行模拟测试，
        // 无需启动服务器
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    /**
     * mockMvc.perform 执行一个请求
     * MockMvcRequestBuilders.get("...") 构造一个请求
     * ResultActions.param 添加请求参数
     * ResultActions.accept 设置返回类型
     * ResultActions.andExpect 添加断言
     * ResultActions.andDo 结果处理器，MockMvcResultHandlers.print()输出整个响应结果信息
     * ResultActions.andReturn 执行完成后返回相应的结果
     */
    @Test
    public void testPost() throws Exception {
        Map map = new HashMap();
        map.put("taskNo", "2");
        String jsonStr = JSONObject.toJSONString(map);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/findTaskByTaskNo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr);
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testInsert() throws Exception {
        Map map = new HashMap();
        map.put("taskNo", "2");
        map.put("status", "1");
        map.put("result", "success");
        String jsonStr = JSONObject.toJSONString(map);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/insertTask")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonStr);
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
