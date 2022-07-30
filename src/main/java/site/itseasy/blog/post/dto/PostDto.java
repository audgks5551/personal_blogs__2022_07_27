package site.itseasy.blog.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.itseasy.base.ModelMapperUtils;
import site.itseasy.blog.post.entity.Post;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;

    public PostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return ModelMapperUtils.getModelMapper().map(this, Post.class);
    }

    public <D> D toVo(Class<D> type) {
        return ModelMapperUtils.getModelMapper().map(this, type);
    }
}
