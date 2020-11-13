import com.thatday.user.UserApplication;
import com.thatday.user.modules.user.entity.Dir;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.service.DirService;
import com.thatday.user.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
public class TestApp {

    @Autowired
    UserService userService;

    @Autowired
    DirService dirService;

    @Test
    @Transactional
    @Commit
    public void a() {
        User user = new User();
        user.setNickname("");

        Dir dir = new Dir();
        dir.setTitle("afafaf");
        dirService.saveOrUpdate(dir);

        user.setDirs(new HashSet<>());
        user.getDirs().add(dir);

        userService.saveOrUpdate(user);
    }

    @Test
    @Transactional
    public void aVoid() {
        List<User> userList = userService.getDao().findAll();
        System.out.println(userList);
    }
}
