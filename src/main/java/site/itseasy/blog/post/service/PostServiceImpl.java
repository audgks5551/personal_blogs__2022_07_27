package site.itseasy.blog.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.entity.Post;
import site.itseasy.blog.post.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    @Override
    public PostDto register(PostDto articleDto) {
        Post article = articleDto.toEntity();

        repository.save(article);

        return article.toDto();
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
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
    public PostDto modify(PostDto articleDto) {
        Long id = articleDto.getId();

        if (id == null || id < 1) {
            log.info("id가 존재하지 않습니다.");
            return null;
        }

        return repository.findById(id)
                .map(article -> article.modifyByDto(articleDto).toDto())
                .orElseGet(() -> this.register(articleDto));
    }

    @Override
    public List<PostDto> listAll() {
        return repository.findAll()
                .stream().map(article -> article.toDto())
                .collect(Collectors.toList());
    }

    @Override
    public PostDto findById(Long postId) {
        return repository.findById(postId).get().toDto();
    }
}
