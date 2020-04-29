package springBoardProject.dao.postDao;

import springBoardProject.domain.Post;

import java.util.List;

public interface PostDao {
    void add(Post post);
    Post get(int id);
    int getCount();
    List<Post> getPage(int index);
    void update(Post post);

    // for test
    List<Post> getName(String name);
    void deleteAll();
    List<Post> getAll();
}