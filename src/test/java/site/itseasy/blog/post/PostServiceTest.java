package site.itseasy.blog.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.repository.PostRepository;
import site.itseasy.blog.post.service.PostService;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void beforeEach() {
        postRepository.truncateArticle();
    }

    @Test
    public void 게시글_생성() {
        // given
        PostDto postDto = new PostDto(3L, "제목1", "내용1");

        // when
        PostDto savedPostDto = postService.register(postDto);

        // given
        assertThat(savedPostDto.getId()).isNotNull();
    }

    @Test
    public void 게시글id를_통한_삭제() {
        // given
        PostDto postDto = new PostDto("제목1", "내용1");
        PostDto savedPostDto = postService.register(postDto);

        // when
        boolean isSuccess = postService.deleteById(savedPostDto.getId());

        // given
        assertThat(isSuccess).isTrue();
    }

    @Test
    public void 게시글id를_통해_삭제할때_실패시_false반환() {
        // given
        long id = 1;

        // when
        boolean isSuccess = postService.deleteById(id);

        // given
        assertThat(isSuccess).isFalse();
    }

    @Test
    public void 게시글_수정() {
        // given
        PostDto postDto = new PostDto("제목1", "내용1");
        PostDto savedPostDto = postService.register(postDto);
        savedPostDto.setTitle("제목2");
        savedPostDto.setContent(null); // modelMapper에서 null값은 변환할 때 적용되지 않는다.

        // when
        PostDto modifiedArticleDto = postService.modify(savedPostDto);

        // given
        assertThat(modifiedArticleDto.getTitle()).isEqualTo(savedPostDto.getTitle());
    }

    @Test
    public void 게시글Dto에_id가_없을시_실패시_null_반환() {
        // given
        PostDto postDto = new PostDto("제목1", "내용1");

        // when
        PostDto modifiedPostDto = postService.modify(postDto);

        // given
        assertThat(modifiedPostDto).isNull();
    }

    @Test
    public void 게시글을_수정할때_게시글이_없으면_null_반환() {
        // given
        PostDto postDto = new PostDto(1L, "제목1", "내용1");

        // when
        PostDto modifiedPostDto = postService.modify(postDto);

        // given
        assertThat(modifiedPostDto).isNull();
    }

    @Test
    public void 게시글의_모든_리스트_조회() {
        // given
        PostDto postDto1 = new PostDto("제목1", "내용1");
        PostDto postDto2 = new PostDto("제목1", "내용1");
        PostDto postDto3 = new PostDto("제목1", "내용1");
        postService.register(postDto1);
        postService.register(postDto2);
        postService.register(postDto3);

        // when
        List<PostDto> articleDtoList = postService.listAll();

        // given
        int size = articleDtoList.size();
        assertThat(size).isEqualTo(3);
    }
}
