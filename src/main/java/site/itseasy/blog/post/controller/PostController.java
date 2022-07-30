package site.itseasy.blog.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.itseasy.blog.post.assembler.PostModelAssembler;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.form.PostForm;
import site.itseasy.blog.post.response.ResponsePost;
import site.itseasy.blog.post.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final PostModelAssembler assembler;

    @PostMapping("/new")
    public ResponseEntity<EntityModel<ResponsePost>> newPost(@RequestBody PostForm articleForm) {
        PostDto postDto = service.register(articleForm.toDto());

        return ResponseEntity.ok(assembler.toModel(postDto));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<EntityModel<ResponsePost>> one(@PathVariable Long postId) {
        PostDto postDto = service.findById(postId);

        return ResponseEntity.ok(assembler.toModel(postDto));
    }

    @GetMapping("/posts")
    public ResponseEntity<CollectionModel<EntityModel<ResponsePost>>> all() {
        List<PostDto> postDtos = service.listAll();

        return ResponseEntity.ok(assembler.toCollectionModel(postDtos));
    }
}
