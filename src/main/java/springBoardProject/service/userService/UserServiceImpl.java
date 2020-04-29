package springBoardProject.service.userService;

import springBoardProject.dao.userDao.UserDao;
import springBoardProject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserDao userDao;

    private int a;

    public void setA(int a) {
        this.a = a;
    }

    public void add(User user) {
        userDao.add(user);
    }

    public User get(String id, String password) {
        return userDao.get(id, password);
    }
}