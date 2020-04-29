package springBoardProject.service.userService;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import springBoardProject.dao.userDao.UserDao;
import springBoardProject.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"/test-applicationContext.xml", "/test-dispatcher-servlet.xml"})
@Transactional
public class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired
    DefaultListableBeanFactory bf;

    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        this.user1 = new User("kimhanju", "pass1");
        this.user2 = new User("gangsubeen", "pass2");
        this.user3 = new User("leegyutae", "pass3");
    }

    @Test
    public void addAndGet() {
        for(String name : bf.getBeanDefinitionNames())
            System.out.println(name + "\t" + bf.getBean(name).getClass().getName());
        userDao.deleteAll();
        assert userDao.getCount() == 0;

        userService.add(user1);
        userService.add(user2);
        assert userDao.getCount() == 2;

        User userget1 = userService.get(user1.getId(), user1.getPassword());
        checkSameUser(userget1, user1);

        User userget2 = userService.get(user2.getId(), user2.getPassword());
        checkSameUser(userget2, user2);
    }

    private void checkSameUser(User user1, User user2) {
        assert user1.getId().equals(user2.getId());
        assert user1.getPassword().equals(user2.getPassword());
    }
}