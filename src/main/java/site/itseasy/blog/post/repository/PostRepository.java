package site.itseasy.blog.post.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "truncate article", nativeQuery = true)
    void truncateArticle();
}
