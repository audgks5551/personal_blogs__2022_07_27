package site.itseasy.blog.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.post.entity.Post;
import site.itseasy.blog.post.repository.PostRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void beforeEach() {
        postRepository.truncateArticle();
    }

    @Test
    public void 게시글_생성() {
        // given
        Post post = new Post("제목1", "내용1");

        // when
        postRepository.save(post);

        // then
        assertThat(post.getId()).isNotNull();
    }

    @Test
    public void 게시글의_id로_게시글_삭제() {
        // given
        Post post1 = new Post("제목1", "내용1");
        Post post2 = new Post("제목2", "내용2");
        Post post3 = new Post("제목2", "내용2");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // when
        postRepository.deleteById(post1.getId());

        // then
        assertThat(postRepository.count()).isEqualTo(2);
    }

    @Test
    public void 게시글entity로_게시글_삭제() {
        // given
        Post post1 = new Post("제목1", "내용1");
        Post post2 = new Post("제목2", "내용2");
        Post post3 = new Post("제목2", "내용2");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // when
        postRepository.delete(post1);

        // then
        assertThat(postRepository.count()).isEqualTo(2);
    }

    @Test
    public void 변경감지를_통한_게시글_수정() {
        // given
        Post post = new Post("제목1", "내용1");
        postRepository.save(post);

        // when
        post.setTitle("제목2");

        // then
        Post findArticle = postRepository.findById(post.getId()).get();
        assertThat(findArticle.getContent()).isEqualTo(post.getContent());
    }

    @Test
    public void 게시글id로_게시글_삭제() {
        // given
        Post post = new Post("제목1", "내용1");
        postRepository.save(post);

        // when
        postRepository.deleteById(post.getId());

        // then
        assertThat(postRepository.count()).isZero();
    }

    @Test
    public void 게시글객체로_게시글_삭제() {
        // given
        Post post = new Post("제목1", "내용1");
        postRepository.save(post);

        // when
        postRepository.delete(post);

        // then
        assertThat(postRepository.count()).isZero();
    }

    @Test
    public void 게시글_모두_불러오기() {
        // given
        Post post1 = new Post("제목1", "내용1");
        Post post2 = new Post("제목2", "내용2");
        Post post3 = new Post("제목2", "내용2");
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // when
        List<Post> articles = postRepository.findAll();

        // then
        articles.forEach(article ->
                assertThat(article).isIn(post1, post2, post3)
        );
    }
}
