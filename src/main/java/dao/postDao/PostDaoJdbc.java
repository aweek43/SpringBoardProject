package dao.postDao;

import dao.Jdbc;
import domain.Post;
import domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PostDaoJdbc extends Jdbc implements PostDao {
    private SimpleDateFormat dataFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    private String time;
    public static final int postPageNum = 5;

    private RowMapper<Post> postMapper = new RowMapper<Post>() {
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            Post post = new Post();
            post.setId(resultSet.getInt("id"));
            post.setAuthor(new User(resultSet.getString("author")));
            post.setTitle(resultSet.getString("title"));
            post.setContent(resultSet.getString("content"));
            post.setGeneratedDate(resultSet.getString("generatedDate"));
            post.setRevisedDate(resultSet.getString("revisedDate"));
            return post;
        }
    };

    public void add(Post post) {
        time = dataFormat.format(new Date());
        this.jdbcTemplate.update(this.sqlService.getSql("postAdd"),
                post.getAuthor().getId(), post.getTitle(), post.getContent(), time, time);
    }

    public Post get(int id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("postGet"), new Object[]{id}, this.postMapper);
    }

    public List<Post> getName(String name) {
        return this.jdbcTemplate.query(this.sqlService.getSql("postGetName"), new Object[]{name}, this.postMapper);
    }

    public List<Post> getAll() {
        return this.jdbcTemplate.query(this.sqlService.getSql("postGetAll"), this.postMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update(this.sqlService.getSql("postDeleteAll"));
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("postGetCount"), Integer.class);
    }

    public List<Post> getPage(int index) {
        return this.jdbcTemplate.query(this.sqlService.getSql("postGetPage"), new Object[]{index}, this.postMapper);
    }

    public void update(Post post) {
        time = dataFormat.format(new Date());
        this.jdbcTemplate.update(this.sqlService.getSql("postUpdate"),
                post.getTitle(), post.getContent(), time, post.getId());
    }
}
