package site.itseasy.blog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.form.PostForm;
import site.itseasy.blog.post.service.PostService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Test
    public void mock_테스트() throws Exception {
        // when
        ResultActions resultActions = mvc.perform(get("/test"))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @Test
    public void 게시글_생성() throws Exception {
        // given
        PostForm articleForm = new PostForm("제목1", "내용1");

        // when
        ResultActions resultActions = mvc.perform(
                post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleForm))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(notNullValue())));
    }

    @Test
    public void 게시글_상세보기() throws Exception {
        // given
        PostDto articleDto = postService.register(new PostDto("제목1", "내용1"));

        // when
        ResultActions resultActions = mvc.perform(
                        get("/posts/%d".formatted(articleDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(notNullValue())));
    }

    @Test
    public void 게시글_리스트() throws Exception {
        // given
        PostDto postDto1 = postService.register(new PostDto("제목1", "내용1"));
        postService.register(new PostDto("제목2", "내용2"));
        postService.register(new PostDto("제목3", "내용3"));

        // when
        ResultActions resultActions = mvc.perform(
                        get("/posts")
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.responsePostList", hasSize(3)))
                .andExpect(jsonPath("$._embedded.responsePostList[0].id").value(postDto1.getId()))
                .andExpect(jsonPath("$._embedded.responsePostList[0].title").value(postDto1.getTitle()))
                .andExpect(jsonPath("$._embedded.responsePostList[0].content").value(postDto1.getContent()));
    }

    @Test
    public void 게시글_수정() throws Exception {
        // given
        PostDto postDto = postService.register(new PostDto("제목1", "내용1"));

        PostForm postForm = new PostForm("제목2", "내용1");

        // when
        ResultActions resultActions = mvc.perform(
                        put("/posts/%d".formatted(postDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postForm))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postDto.getId()))
                .andExpect(jsonPath("$.title").value(postForm.getTitle()))
                .andExpect(jsonPath("$.content").value(postForm.getContent()));
    }

    @Test
    public void 게시글_삭제() throws Exception {
        // given
        PostDto postDto = postService.register(new PostDto("제목1", "내용1"));

        // when
        ResultActions resultActions = mvc.perform(
                        delete("/posts/%d".formatted(postDto.getId()))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isNoContent());
    }

    @Test
    public void 게시글_수정할_때_게시글이_없으면_생성() throws Exception {
        // given
        Long postId = 1000L;
        PostForm postForm = new PostForm("제목2", "내용1");

        // when
        ResultActions resultActions = mvc.perform(
                        put("/posts/%d".formatted(postId))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postForm))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(postForm.getTitle()))
                .andExpect(jsonPath("$.content").value(postForm.getContent()));
    }
}
