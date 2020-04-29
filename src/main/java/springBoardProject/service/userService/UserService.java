package springBoardProject.service.userService;

import springBoardProject.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {
    void add(User user);

    @Transactional(readOnly = true)
    User get(String id, String password);
}