package site.itseasy.blog.article;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.itseasy.blog.article.dto.ArticleDto;
import site.itseasy.blog.article.repository.ArticleRepository;
import site.itseasy.blog.article.service.ArticleService;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    public void beforeEach() {
        articleRepository.truncateArticle();
    }

    @Test
    public void 게시글_생성() {
        // given
        ArticleDto articleDto = new ArticleDto(3L, "제목1", "내용1");

        // when
        ArticleDto savedArticleDto = articleService.register(articleDto);

        // given
        assertThat(savedArticleDto.getId()).isNotNull();
    }

    @Test
    public void 게시글id를_통한_삭제() {
        // given
        ArticleDto articleDto = new ArticleDto("제목1", "내용1");
        ArticleDto savedArticleDto = articleService.register(articleDto);

        // when
        boolean isSuccess = articleService.deleteById(savedArticleDto.getId());

        // given
        assertThat(isSuccess).isTrue();
    }

    @Test
    public void 게시글id를_통해_삭제할때_실패시_false반환() {
        // given
        long id = 1;

        // when
        boolean isSuccess = articleService.deleteById(id);

        // given
        assertThat(isSuccess).isFalse();
    }

    @Test
    public void 게시글_수정() {
        // given
        ArticleDto articleDto = new ArticleDto("제목1", "내용1");
        ArticleDto savedArticleDto = articleService.register(articleDto);
        savedArticleDto.setTitle("제목2");
        savedArticleDto.setContent(null); // modelMapper에서 null값은 변환할 때 적용되지 않는다.

        // when
        ArticleDto modifiedArticleDto = articleService.modify(savedArticleDto);

        // given
        assertThat(modifiedArticleDto.getTitle()).isEqualTo(savedArticleDto.getTitle());
    }

    @Test
    public void 게시글Dto에_id가_없을시_실패시_null_반환() {
        // given
        ArticleDto articleDto = new ArticleDto("제목1", "내용1");

        // when
        ArticleDto modifiedArticleDto = articleService.modify(articleDto);

        // given
        assertThat(modifiedArticleDto).isNull();
    }

    @Test
    public void 게시글을_수정할때_게시글이_없으면_null_반환() {
        // given
        ArticleDto articleDto = new ArticleDto(1L, "제목1", "내용1");

        // when
        ArticleDto modifiedArticleDto = articleService.modify(articleDto);

        // given
        assertThat(modifiedArticleDto).isNull();
    }

    @Test
    public void 게시글의_모든_리스트_조회() {
        // given
        ArticleDto articleDto1 = new ArticleDto("제목1", "내용1");
        ArticleDto articleDto2 = new ArticleDto("제목1", "내용1");
        ArticleDto articleDto3 = new ArticleDto("제목1", "내용1");
        articleService.register(articleDto1);
        articleService.register(articleDto2);
        articleService.register(articleDto3);

        // when
        List<ArticleDto> articleDtoList = articleService.listAll();

        // given
        int size = articleDtoList.size();
        assertThat(size).isEqualTo(3);
    }


}
