package site.itseasy.blog.article.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "truncate article", nativeQuery = true)
    void truncateArticle();
}
