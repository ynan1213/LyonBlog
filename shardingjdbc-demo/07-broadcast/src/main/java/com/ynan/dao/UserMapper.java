package com.ynan.dao;

import com.ynan.entity.Dic;
import com.ynan.entity.Dog;
import com.ynan.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author yuannan
 * @Date 2021/11/4 21:19
 */
@Mapper
public interface UserMapper {

    List<User> listAll();

    List<Dog> listAllDog();

    List<Dic> listAllDic();

    int insertUser(User user);

    int insertDog(Dog dog);

    int insertDic(Dic dic);

}
