import com.ynan.TestMain;
import com.ynan.dao.UserMapper;
import com.ynan.entity.Dic;
import com.ynan.entity.User;
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
public class Test08 {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User(null, "name" + i, i%2+1, "");
            userMapper.insertUser(user);
        }
    }

    @Test
    public void testInsertDic() {
        Dic dic = new Dic(null, 1, "启用");
        Dic dic1 = new Dic(null, 2, "禁用");
        userMapper.insertDic(dic);
        userMapper.insertDic(dic1);
    }

    @Test
    public void listAll() {
        userMapper.listAll().forEach(System.out::println);
    }

    @Test
    public void listAllDic() {
        userMapper.listAllDic().forEach(System.out::println);
    }
}
