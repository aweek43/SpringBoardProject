package service.userService;

import dao.userDao.UserDao;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserDao userDao;

    public void add(User user) {
        userDao.add(user);
    }

    public User get(String id, String password) {
        return userDao.get(id, password);
    }
}
