package springBoardProject.controller;

import org.springframework.web.bind.annotation.*;
import springBoardProject.domain.Post;
import springBoardProject.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import springBoardProject.service.postService.PostService;
import springBoardProject.service.userService.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin
public class HomeController {
    private UserService userService;
    @Autowired private PostService postService;
    // @Autowired private ObjectMapper objectMapper;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity showHome() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PostMapping(value = "/requestSignUp")
    public ResponseEntity requestSignUp(User user) {
        try {
            userService.add(user);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity("duplicate key", HttpStatus.CONFLICT);
        }
        return new ResponseEntity("success", HttpStatus.CREATED);
    }

    @PostMapping(value = "/requestLogin")
    public ResponseEntity requestLogin(User user) {
        User loginUser = null;
        try {
            loginUser = userService.get(user.getId(), user.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("empty result", HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Authorization", loginUser.getId());
        return ResponseEntity.ok().headers(headers).body("success");
    }

    @GetMapping(value = "/postList")
    @ResponseBody
    public ResponseEntity postList(@RequestParam("page") int page) {
        List<Post> p = postService.getPage(page);
        return ResponseEntity.ok(p);
    }

     public static class PostList {
         private List<Post> postList;
         public List<Post> getPostList() {
             return postList;
         }
         public void setPostList(List<Post> postList) {
             this.postList = postList;
         }
    }

    private String getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return user.getId();
    }

    @RequestMapping(value = "/readPost", method = RequestMethod.GET)
    public String readPost(HttpServletRequest request, ModelMap modelMap) {
        Post post = null;
        String userId = getUserId(request);
        int postId = Integer.parseInt(request.getParameter("postId"));
        try {
            post = postService.get(postId);
        } catch (EmptyResultDataAccessException e) {
            return "postList";
        }
        modelMap.addAttribute("userId", userId);
        modelMap.addAttribute("postId", postId);
        modelMap.addAttribute("author", post.getAuthor().getId());
        modelMap.addAttribute("title", post.getTitle());
        modelMap.addAttribute("content", post.getContent());
        modelMap.addAttribute("generatedDate", post.getGeneratedDate());
        return "postOne";
    }

    private List<Post> hideIdList(List<Post> postList) {
        for(Post post : postList) {
            post.getAuthor().setId(post.getAuthor().getId());
        }
        return postList;
    }

    @RequestMapping(value = "/writePost", method = RequestMethod.GET)
    public String writePost(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("userId", request.getParameter("userId"));
        return "writePost";
    }

    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public String savePost(HttpServletRequest request, ModelMap modelMap) {
        Post post = new Post(new User(request.getParameter("userId")),
                request.getParameter("title"), request.getParameter("content"));
        postService.add(post);
        // modelMap = getPostList(0);
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public String updatePost(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("postId", request.getParameter("postId"));
        modelMap.addAttribute("title", request.getParameter("title"));
        modelMap.addAttribute("content", request.getParameter("content"));
        return "updatePost";
    }

    @RequestMapping(value = "/saveUpdatedPost", method = RequestMethod.POST)
    public String saveUpdatedPost(HttpServletRequest request, ModelMap modelMap) {
        Post post = new Post(Integer.parseInt(request.getParameter("postId")));
        post.setTitle(request.getParameter("title"));
        post.setContent(request.getParameter("content"));
        postService.update(post);
        // modelMap = getPostList(0);
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
    }
}