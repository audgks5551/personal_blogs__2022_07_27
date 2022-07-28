package site.itseasy.blog.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.article.dto.ArticleDto;
import site.itseasy.blog.article.entity.Article;
import site.itseasy.blog.article.repository.ArticleRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public ArticleDto register(ArticleDto articleDto) {
        Article article = articleDto.toEntity();

        articleRepository.save(article);

        return article.toDto();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            articleRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.info("{}", e);
            return false;
        } catch (Exception e) {
            log.info("{}", e);
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public ArticleDto modify(ArticleDto articleDto) {
        Long id = articleDto.getId();

        if (id == null || id < 1) {
            log.info("id가 존재하지 않습니다.");
            return null;
        }

        return articleRepository.findById(id)
                .map(article -> article.modifyByDto(articleDto))
                .map(article -> article.toDto())
                .orElse(null);
    }

    @Override
    public List<ArticleDto> listAll() {
        return articleRepository.findAll()
                .stream().map(article -> article.toDto())
                .toList();
    }
}
