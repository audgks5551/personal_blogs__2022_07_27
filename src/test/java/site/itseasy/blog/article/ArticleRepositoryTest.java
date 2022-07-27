package site.itseasy.blog.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.article.entity.Article;
import site.itseasy.blog.article.repository.ArticleRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void beforeEach() {
        articleRepository.truncateArticle();
    }

    @Test
    public void 게시글_생성() {
        // given
        Article article = new Article("제목1", "내용1");

        // when
        articleRepository.save(article);

        // then
        assertThat(article.getId()).isNotNull();
    }

    @Test
    public void 게시글의_id로_게시글_삭제() {
        // given
        Article article1 = new Article("제목1", "내용1");
        Article article2 = new Article("제목2", "내용2");
        Article article3 = new Article("제목2", "내용2");
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        // when
        articleRepository.deleteById(article1.getId());

        // then
        assertThat(articleRepository.count()).isEqualTo(2);
    }

    @Test
    public void 게시글entity로_게시글_삭제() {
        // given
        Article article1 = new Article("제목1", "내용1");
        Article article2 = new Article("제목2", "내용2");
        Article article3 = new Article("제목2", "내용2");
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        // when
        articleRepository.delete(article1);

        // then
        assertThat(articleRepository.count()).isEqualTo(2);
    }

    @Test
    public void 변경감지를_통한_게시글_수정() {
        // given
        Article article = new Article("제목1", "내용1");
        articleRepository.save(article);

        // when
        article.setTitle("제목2");

        // then
        Article findArticle = articleRepository.findById(article.getId()).get();
        assertThat(findArticle.getContent()).isEqualTo(article.getContent());
    }

    @Test
    public void 게시글id로_게시글_삭제() {
        // given
        Article article = new Article("제목1", "내용1");
        articleRepository.save(article);

        // when
        articleRepository.deleteById(article.getId());

        // then
        assertThat(articleRepository.count()).isZero();
    }

    @Test
    public void 게시글객체로_게시글_삭제() {
        // given
        Article article = new Article("제목1", "내용1");
        articleRepository.save(article);

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isZero();
    }

    @Test
    public void 게시글_모두_불러오기() {
        // given
        Article article1 = new Article("제목1", "내용1");
        Article article2 = new Article("제목2", "내용2");
        Article article3 = new Article("제목2", "내용2");
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        articles.forEach(article ->
                assertThat(article).isIn(article1, article2, article3)
        );
    }
}
