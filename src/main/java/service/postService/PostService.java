package service.postService;

import domain.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PostService {
    void add(Post post);
    void update(Post post);

    @Transactional(readOnly = true)
    Post get(int id);

    @Transactional(readOnly = true)
    List<Post> getAll();

    @Transactional(readOnly = true)
    int getCount();

    @Transactional(readOnly = true)
    List<Post> getPage(int index);
}
