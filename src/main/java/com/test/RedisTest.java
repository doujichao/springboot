package com.test;

import com.config.RedisConfig;
import com.pojo.Department;
import com.pojo.Role;
import com.pojo.User;
import com.repository.UserRedis;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springboot.example.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {
    private static Logger logger= LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    UserRedis userRedis;

    @Before
    public void setup(){
        Department department=new Department();
        department.setName("开发部");

        Role role=new Role();
        role.setRoleName("admin");

        User user=new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDepartment(department);

        List<Role> roles=new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);

        userRedis.delete(user.getName());
        userRedis.add(user.getName(),10l,user);
    }

    @Test
    public void get(){
        User user = userRedis.get("user");
        Assert.assertNotNull(user);
        System.out.println(user);
    }
}
