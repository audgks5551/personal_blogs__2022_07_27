package site.itseasy.blog.post.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.itseasy.base.ModelMapperUtils;
import site.itseasy.blog.post.dto.PostDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {
    private String title;
    private String content;

    public PostDto toDto() {
        return ModelMapperUtils.getModelMapper().map(this, PostDto.class);
    }
}
