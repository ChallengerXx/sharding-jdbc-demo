package com.xx.config.data.property;

import com.xx.dao.entity.ApplicationProperty;
import com.xx.dao.mapper.ApplicationPropertyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class DbPropertyService {

    @Autowired
    ApplicationPropertyMapper applicationPropertyMapper;

    private List<ApplicationProperty> applicationPropertyList;

    public String getProperty(String name){
        if (applicationPropertyList == null){
            applicationPropertyList = applicationPropertyMapper.findAll();
            log.info("系统配置属性获取条数：{}", applicationPropertyList.size());
        }
        if (CollectionUtils.isEmpty(applicationPropertyList)){
            return null;
        }
        for (ApplicationProperty property : applicationPropertyList) {
            if (property.getName().equals(name)){
                return property.getValue();
            }
        }
        return null;
    }
}
