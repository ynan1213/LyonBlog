import com.ynan.TestMain;
import com.ynan.dao.UserMapper;
import com.ynan.entity.User;
import java.sql.SQLException;
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
public class Test02 {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testInsertUser() throws SQLException {
        for (int i = 0; i < 10; i++) {
            User user = new User(null, "name" + i, i, "address" + i);
            userMapper.insertUser(user);
        }
    }
}
