package dao.userDao;

import domain.User;

public interface UserDao {
    void add(User user);
    User get(String id, String password);

    // for test
    void deleteAll();
    int getCount();
}
