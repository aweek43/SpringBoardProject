package controller;

import domain.Post;
import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.postService.PostService;
import service.userService.UserService;
import static dao.postDao.PostDaoJdbc.postPageNum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    private static final Log logger = LogFactory.getLog(HomeController.class);
    @Autowired private UserService userService;
    @Autowired private PostService postService;

    @RequestMapping("/")
    public String showHome() {
        return "home";
    }

    @RequestMapping(value = "/requestSignUp", method = RequestMethod.POST)
    public String requestSignUp(HttpServletRequest request, ModelMap modelMap) {
        try {
            userService.add(new User(request.getParameter("userId"), request.getParameter("password")));
        } catch (DuplicateKeyException e) {
            modelMap.addAttribute("duplicateKey", "이미 존재하는 ID입니다.");
            return "signUp";
        }
        modelMap.addAttribute("success", "회원가입이 완료되었습니다.");
        return "home";
    }

    @RequestMapping(value = "/requestLogin", method = RequestMethod.POST)
    public String requestLogin(HttpServletRequest request, ModelMap modelMap) {
        User user = null;
        try {
            user = userService.get(request.getParameter("userId"), request.getParameter("password"));
        } catch (EmptyResultDataAccessException e) {
            modelMap.addAttribute("emptyResult", "존재하지 않은 ID이거나 비밀번호가 틀렸습니다.");
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        modelMap = getPostList(modelMap, 0);
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
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
        modelMap = getPostList(modelMap, 0);
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
    }

    @RequestMapping(value = "/postList", method = RequestMethod.GET)
    public String postList(HttpServletRequest request, ModelMap modelMap) {
        HttpSession session = request.getSession();
        modelMap = getPostList(modelMap, Integer.parseInt(request.getParameter("page")));
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
    }

    private ModelMap getPostList(ModelMap modelMap, int index) {
        List<Post> postList = getPostPage(index * postPageNum);
        modelMap.addAttribute("page", index);
        modelMap.addAttribute("postList", hideIdList(postList));
        modelMap.addAttribute("pageCount", (int) Math.ceil(postService.getCount() / postPageNum));
        return modelMap;
    }

    private List<Post> getPostPage(int index) {
        return postService.getPage(index);
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
        modelMap = getPostList(modelMap, 0);
        modelMap.addAttribute("userId", getUserId(request));
        return "postList";
    }
}