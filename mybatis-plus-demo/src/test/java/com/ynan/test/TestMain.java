package com.ynan.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ynan.dao.UserMapper;
import com.ynan.entity.User;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2021/11/8 14:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User user = new User("xxx", 18);
        user.setAge(null);
        userMapper.insert(user);
        System.out.println("生成的主键：" + user.getId());
    }

    @Test
    public void testSelect() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getId, 1);
        wrapper.eq(User::getAge, 12);
        wrapper.or();
        wrapper.eq(User::getName, "xxx");
        userMapper.selectList(wrapper);
    }

    @Test
    public void testSelect1() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("name", "age");
        queryWrapper.eq("id", 11);
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }



}
