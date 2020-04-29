package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import springBoardProject.controller.HomeController;
import springBoardProject.domain.Post;
import springBoardProject.domain.User;
import springBoardProject.service.postService.PostService;
import springBoardProject.service.userService.UserService;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static springBoardProject.dao.postDao.PostDaoJdbc.postPageNum;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-applicationContext.xml", "/test-dispatcher-servlet.xml"})
public class ControllerTest {
    private MockMvc mockMvc;
    @Autowired UserService userService;
    @Autowired PostService postService;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private HomeController controller;
    private User user1;
    private User user2;
    private List<Post> postList;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        postList = new ArrayList();
        user1 = new User("userId1", "userPw1");
        user2 = new User("userId2", "userPw2");
        for(int i=0; i < (int)(postPageNum*(2.5)); ++i) {
            postList.add(new Post(user1, "t"+Integer.toString(i) , "c"+Integer.toString(i)));
        }
    }

    @Test
    public void homeTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void requestSignUpTest() throws Exception {
        mockMvc.perform(post("/requestSignUp")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(objectMapper.writeValueAsString(user1))
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void duplicateKeyTest() throws Exception {
        userService.add(user1);
        mockMvc.perform(post("/requestSignUp")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(user1))
                )
                .andExpect(status().isConflict());
    }

    @Test
    public void requestLoginTest() throws Exception {
        userService.add(user1);
        mockMvc.perform(post("/requestLogin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(user1))
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Authorization", user1.getId()));
    }

    @Test
    public void notFoundTest() throws Exception {
        userService.add(user1);
        mockMvc.perform(post("/requestLogin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8")
                .content(objectMapper.writeValueAsString(user2))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void postListTest() throws Exception {
        userService.add(user1);
        for(Post p : postList) {
            postService.add(p);
            sleep(1000);
        }
        HomeController.PostList homePostList = new HomeController.PostList();
        mockMvc.perform(get("/postList?page=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['author']['id']", is(user1.getId())))
                .andExpect(jsonPath("$[0]['title']", is("t"+Integer.toString((int) (postPageNum*(2.5)) - 1))))
                .andExpect(jsonPath("$[0]['content']", is("c"+Integer.toString((int) (postPageNum*(2.5)) - 1))))
                .andExpect(jsonPath("$[" + Integer.toString(postPageNum - 1) + "]['title']", is("t"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum))))
                .andExpect(jsonPath("$[" + Integer.toString(postPageNum - 1) + "]['content']", is("c"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum))));

        mockMvc.perform(get("/postList?page=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['author']['id']", is(user1.getId())))
                .andExpect(jsonPath("$[0]['title']", is("t"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum - 1))))
                .andExpect(jsonPath("$[0]['content']", is("c"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum - 1))))
                .andExpect(jsonPath("$[" + Integer.toString(postPageNum - 1) + "]['title']", is("t"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum - postPageNum))))
                .andExpect(jsonPath("$[" + Integer.toString(postPageNum - 1) + "]['content']", is("c"+Integer.toString((int) (postPageNum*(2.5)) - postPageNum - postPageNum))));

        mockMvc.perform(get("/postList?page=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['author']['id']", is(user1.getId())))
                .andExpect(jsonPath("$[0]['title']", is("t"+Integer.toString((int) (postPageNum*(2.5)) - 2 * postPageNum - 1))))
                .andExpect(jsonPath("$[0]['content']", is("c"+Integer.toString((int) (postPageNum*(2.5)) - 2 * postPageNum - 1))))
                .andExpect(jsonPath("$[" + Integer.toString((int)(postPageNum / 2) - 1) + "]['title']", is("t0")))
                .andExpect(jsonPath("$[" + Integer.toString((int)(postPageNum / 2) - 1) + "]['content']", is("c0")));
    }
}