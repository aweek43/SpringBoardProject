package service.postService;

import dao.postDao.PostDao;
import domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired PostDao postDao;

    public void add(Post post) {
        postDao.add(post);
    }

    public Post get(int id) {
        return postDao.get(id);
    }

    public List<Post> getAll() {
        return postDao.getAll();
    }

    public int getCount() {
        return postDao.getCount();
    }

    public List<Post> getPage(int index) {
        return postDao.getPage(index);
    }

    public void update(Post post) {
        postDao.update(post);
    }
}