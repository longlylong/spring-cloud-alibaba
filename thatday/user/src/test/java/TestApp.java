import com.thatday.user.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = UserApplication.class)
@RunWith(SpringRunner.class)
public class TestApp {


    @Test
    @Transactional
    public void a() {
    }
}
