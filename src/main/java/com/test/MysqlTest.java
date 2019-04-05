package com.test;

import com.config.JpaConfiguration;
import com.pojo.Department;
import com.pojo.Role;
import com.pojo.User;
import com.repository.DepartmentRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import springboot.example.Application;

import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class})
@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MysqlTest {
    private static Logger logger= LoggerFactory.getLogger(MysqlTest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void initData(){
        //清空现有数据
        userRepository.deleteAll();
        roleRepository.deleteAll();
        departmentRepository.deleteAll();
        //新增部门数据
        Department department=new Department();
        department.setName("开发部");
        departmentRepository.save(department);
        Assert.notNull(department.getId());
        //新增角色数据
        Role role=new Role();
        role.setRoleName("admin");
        roleRepository.save(role);
        Assert.notNull(role.getRoleId());

        User user=new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDepartment(department);
        List<Role> roles = roleRepository.findAll();
        Assert.notNull(roles);
        user.setRoles(roles);
        userRepository.save(user);
        Assert.notNull(user);
    }

    @Test
    public void findPage(){
        Pageable pageable=new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Page<User> page = userRepository.findAll(pageable);
        Assert.notNull(page);
        for (User user:page.getContent()){
            logger.info("======user======= user name:"+user.getName()+"department name:"
                    );
        }
    }
}
