package site.itseasy.blog.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.itseasy.base.ModelMapperUtils;
import site.itseasy.blog.post.dto.PostDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long id;

    @Setter
    @Column(length = 100, nullable = false)
    private String title;

    @Setter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostDto toDto() {
        return ModelMapperUtils.getModelMapper().map(this, PostDto.class);
    }

    public Post modifyByDto(PostDto articleDto) {
        ModelMapperUtils.getModelMapper().map(articleDto, this);
        return this;
    }
}
