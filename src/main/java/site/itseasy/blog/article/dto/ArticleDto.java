package site.itseasy.blog.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.itseasy.base.ModelMapperUtils;
import site.itseasy.blog.article.entity.Article;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ArticleDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Article toEntity() {
        return ModelMapperUtils.getModelMapper().map(this, Article.class);
    }
}
