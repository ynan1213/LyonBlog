import com.ynan.TestMain;
import com.ynan.dao.UserMapper;
import com.ynan.entity.Dog;
import com.ynan.entity.User;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author yuannan
 * @Date 2022/3/12 10:55
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestMain.class)
public class Test03 {

    @Resource
    private UserMapper userMapper;

    @Test
    public void al() {
        System.out.println((1 % 4) / 2 + 1 + " - " + 1 % 2);
        System.out.println((2 % 4) / 2 + 1 + " - " + 2 % 2);
        System.out.println((3 % 4) / 2 + 1 + " - " + 3 % 2);
        System.out.println((4 % 4) / 2 + 1 + " - " + 4 % 2);
    }

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User(null, "name" + i, i, "address" + i);
            userMapper.insertUser(user);
        }
    }

    @Test
    public void testIn() {
        List<Long> idList = Arrays.asList(11l);
        List<User> userList = userMapper.in(idList);
        userList.forEach(System.out::println);
    }

    /**
     * inline 模式不支持范围查找
     */
    @Test
    public void testRange() {
        List<User> userList = userMapper.betweenAnd(1l, 2l);
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsertDog() {
        for (int i = 0; i < 10; i++) {
            Dog dog = new Dog(null, "旺财" + i, i);
            userMapper.insertDog(dog);
        }
    }
}
