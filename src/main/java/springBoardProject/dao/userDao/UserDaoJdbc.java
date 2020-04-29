package springBoardProject.dao.userDao;

import springBoardProject.dao.Jdbc;
import springBoardProject.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoJdbc extends Jdbc implements UserDao {

    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    };

    public void add(User user) {
        this.jdbcTemplate.update(this.sqlService.getSql("userAdd"),
                user.getId(), user.getPassword());
    }

    public User get(String id, String password) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
                new Object[]{id, password}, this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("userDeleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"), Integer.class);
    }
}
