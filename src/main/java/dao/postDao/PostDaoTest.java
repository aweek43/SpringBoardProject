package dao.postDao;

import context.AppContext;
import dao.userDao.UserDao;
import domain.Post;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import static dao.postDao.PostDaoJdbc.postPageNum;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppContext.class)
@Transactional
public class PostDaoTest {
    @Autowired PostDao postDao;
    @Autowired UserDao userDao;
    @Autowired DataSource dataSource;

    private Post post1;
    private Post post2;
    private Post post3;
    private Post post4;
    private Post post5;
    private Post post6;
    private User user1;
    private User user2;

    @Before
    public void setUp() {
        this.user1 = new User("kimhanju", "pass1");
        this.user2 = new User("leegyutae", "pass2");
        this.post1 = new Post(user1, "title1", "content1");
        this.post2 = new Post(user2, "title2", "content2");
        this.post3 = new Post(user1, "title3", "content3");
        this.post4 = new Post(user1, "title4", "content4");
        this.post5 = new Post(user1, "title5", "content5");
        this.post6 = new Post(user1, "title6", "content6");

        postDao.deleteAll();
        userDao.deleteAll();
        userDao.add(user1);
        userDao.add(user2);
    }

    @Test
    public void addAndGet() {
        assert postDao.getCount() == 0;

        postDao.add(post1);
        postDao.add(post2);
        assert userDao.getCount() == 2;

        Post postget1 = postDao.getName(post1.getAuthor().getId()).get(0);
        checkSamePost(postget1, post1);

        Post postget2 = postDao.getName(post2.getAuthor().getId()).get(0);
        checkSamePost(postget2, post2);
    }

    private void checkSamePost(Post post1, Post post2) {
        assert post1.getAuthor().getId().equals(post2.getAuthor().getId());
        assert post1.getTitle().equals(post2.getTitle());
        assert post1.getContent().equals(post2.getContent());
    }

    @Test
    public void count() {
        assert postDao.getCount() == 0;

        postDao.add(post1);
        assert postDao.getCount() == 1;

        postDao.add(post2);
        assert postDao.getCount() == 2;
    }

    @Test
    public void emptyPost() {
        assert postDao.getCount() == 0;

        assert postDao.getName("unknown").size() == 0;
    }

    @Test
    public void getPage() throws InterruptedException {
        assert postDao.getCount() == 0;

        postDao.add(post1);
        Thread.sleep(1000);
        postDao.add(post2);
        Thread.sleep(1000);
        postDao.add(post3);
        Thread.sleep(1000);
        postDao.add(post4);
        Thread.sleep(1000);
        postDao.add(post5);
        Thread.sleep(1000);
        postDao.add(post6);
        assert postDao.getCount() == 6;

        List<Post> postPage = postDao.getPage(0);
        assert postPage.size() == postPageNum;
        checkSamePost(postPage.get(0), post6);
        checkSamePost(postPage.get(1), post5);
        checkSamePost(postPage.get(2), post4);
        checkSamePost(postPage.get(3), post3);
        checkSamePost(postPage.get(4), post2);
    }

    @Test
    public void update() {
        assert postDao.getCount() == 0;

        postDao.add(post1);
        assert postDao.getCount() == 1;
        Post getPost = postDao.getAll().get(0);
        checkSamePost(getPost, post1);

        getPost.setTitle("new title");
        getPost.setContent("new content");
        postDao.update(getPost);
        assert postDao.getCount() == 1;

        Post updatePost = postDao.getAll().get(0);
        checkSamePost(getPost, updatePost);
        assert getPost.getId() == updatePost.getId();
    }
}
