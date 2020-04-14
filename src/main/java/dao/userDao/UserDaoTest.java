package dao.userDao;

import context.AppContext;
import domain.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppContext.class)
@Transactional
public class UserDaoTest {
    @Autowired UserDao userDao;
    @Autowired DataSource dataSource;

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
        userDao.deleteAll();
        assert userDao.getCount() == 0;

        userDao.add(user1);
        userDao.add(user2);
        assert userDao.getCount() == 2;

        User userget1 = userDao.get(user1.getId(), user1.getPassword());
        checkSameUser(userget1, user1);

        User userget2 = userDao.get(user2.getId(), user2.getPassword());
        checkSameUser(userget2, user2);
    }

    private void checkSameUser(User user1, User user2) {
        assert user1.getId().equals(user2.getId());
        assert user1.getPassword().equals(user2.getPassword());
    }

    @Test
    public void count() {
        userDao.deleteAll();
        assert userDao.getCount() == 0;

        userDao.add(user1);
        assert userDao.getCount() == 1;

        userDao.add(user2);
        assert userDao.getCount() == 2;

        userDao.add(user3);
        assert userDao.getCount() == 3;
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserIdFailure() {
        userDao.deleteAll();
        assert userDao.getCount() == 0;

        userDao.get("unknown", "pass1");
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserPasswordFailure() {
        userDao.deleteAll();
        assert userDao.getCount() == 0;

        userDao.add(user1);
        userDao.get(user1.getId(), "unknown");
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateKey() {
        userDao.deleteAll();

        userDao.add(user1);
        userDao.add(user1);
    }

    @Test
    public void sqlExceptionTranslate() {
        userDao.deleteAll();

        try {
            userDao.add(user1);
            userDao.add(user1);
        }
        catch(DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);

            assert set.translate(null, null, sqlEx) instanceof DuplicateKeyException;
        }
    }
}