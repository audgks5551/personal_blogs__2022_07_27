package site.itseasy.blog.post.service;

import org.springframework.stereotype.Service;
import site.itseasy.blog.post.dto.PostDto;

import java.util.List;

@Service
public interface PostService {
    PostDto register(PostDto articleDto);

    boolean deleteById(Long id);

    PostDto modify(PostDto savedArticleDto);

    List<PostDto> listAll();

    PostDto findById(Long postId);
}
