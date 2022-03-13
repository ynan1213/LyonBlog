import com.ynan.TestMain;
import com.ynan.dao.UserMapper;
import com.ynan.entity.Dic;
import com.ynan.entity.Dog;
import com.ynan.entity.User;
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
public class Test07 {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User(null, "name" + i, i, "address" + i);
            userMapper.insertUser(user);
        }
    }

    @Test
    public void testInsertDog() {
        for (int i = 0; i < 10; i++) {
            Dog dog = new Dog(null, "旺财" + i, i);
            userMapper.insertDog(dog);
        }
    }

    @Test
    public void testInsertDic() {
        for (int i = 0; i < 3; i++) {
            Dic dic = new Dic(null, "name" + i, "value" + i);
            userMapper.insertDic(dic);
        }
    }

    @Test
    public void listAll() {
        userMapper.listAll().forEach(System.out::println);
    }
    @Test
    public void listAllDog() {
        userMapper.listAllDog().forEach(System.out::println);
    }
    @Test
    public void listAllDic() {
        userMapper.listAllDic().forEach(System.out::println);
    }
}
