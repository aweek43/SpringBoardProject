package service.postService;

import context.AppContext;
import dao.postDao.PostDao;
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
import service.userService.UserService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = AppContext.class)
@Transactional
public class PostServiceTest {
    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired PostService postService;
    @Autowired PostDao postDao;

    private User user1;
    private User user2;
    private Post post1;
    private Post post2;
    private Post post3;

    @Before
    public void setUp() {
        this.user1 = new User("kimhanju", "pass1");
        this.user2 = new User("gangsubeen", "pass2");
        this.post1 = new Post(user1, "title1", "content1");
        this.post2 = new Post(user2, "title2", "content2");
        this.post3 = new Post(user1, "title3", "content3");

        userDao.deleteAll();
        postDao.deleteAll();
    }

    @Test
    public void addAndGet() {
        assert userDao.getCount() == 0;
        assert postDao.getCount() == 0;

        userService.add(user1);
        userService.add(user2);
        assert userDao.getCount() == 2;

        postService.add(post1);
        postService.add(post2);
        postService.add(post3);
        assert postDao.getCount() == 3;

        List<Post> posts = postService.getAll();
        assert posts.size() == 3;

        checkSamePost(posts.get(0), post1);
        checkSamePost(posts.get(1), post2);
        checkSamePost(posts.get(2), post3);
    }

    private void checkSamePost(Post post1, Post post2) {
        assert post1.getAuthor().getId().equals(post2.getAuthor().getId());
        assert post1.getTitle().equals(post2.getTitle());
        assert post1.getContent().equals(post2.getContent());
    }
}
