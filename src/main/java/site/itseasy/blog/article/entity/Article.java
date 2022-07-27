package site.itseasy.blog.article.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "article_id", updatable = false)
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String title;

    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
