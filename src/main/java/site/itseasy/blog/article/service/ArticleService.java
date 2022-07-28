package site.itseasy.blog.article.service;

import org.springframework.stereotype.Service;
import site.itseasy.blog.article.dto.ArticleDto;

import java.util.List;

@Service
public interface ArticleService {
    ArticleDto register(ArticleDto articleDto);

    boolean deleteById(Long id);

    ArticleDto modify(ArticleDto savedArticleDto);

    List<ArticleDto> listAll();
}
