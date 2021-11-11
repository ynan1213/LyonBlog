package com.ynan.convert;

import com.ynan.entity.Dog;
import com.ynan.entity.DogVO;
import com.ynan.entity.User;
import com.ynan.entity.UserVO;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @Author yuannan
 * @Date 2021/11/6 20:38
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserConvert {

    public static UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(source = "bir", target = "birthday", dateFormat = "yyyy-MM-dd HH:ss:mm")
    @Mapping(source = "price", target = "pri", numberFormat = "#00.00")
    @Mapping(source = "dog", target = "dogVO")
    public abstract UserVO user2UserVO(User user);

    @Mapping(source = "name", target = "dname")
    @Mapping(source = "age", target = "dage")
    public abstract DogVO dog2DogVO(Dog dog);

    @AfterMapping
    public void convertAfter(User user, @MappingTarget UserVO userVO) {
        if (user.getMoney() != null && user.getMoney().size() > 0) {
            userVO.setHasMoney(true);
        } else {
            userVO.setHasMoney(false);
        }
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "name", target = "name")
    public abstract UserVO user2222UserVO(User user);

}
