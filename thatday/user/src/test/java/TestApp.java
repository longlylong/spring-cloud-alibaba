import com.thatday.user.UserApplication;
import com.thatday.user.modules.user.entity.Dir;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.service.DirService;
import com.thatday.user.modules.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
public class TestApp {

    @Autowired
    UserService userService;

    @Autowired
    DirService dirService;

    @Test
    public void a() {
        User user = userService.save("");
        Dir dir = dirService.save(user.getId());
        System.out.println(dir);

    }
}
