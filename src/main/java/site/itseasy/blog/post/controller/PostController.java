package site.itseasy.blog.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.itseasy.blog.post.assembler.PostModelAssembler;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.form.PostForm;
import site.itseasy.blog.post.response.ResponsePost;
import site.itseasy.blog.post.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final PostModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<ResponsePost>> newPost(@RequestBody PostForm articleForm) {
        PostDto postDto = service.register(articleForm.toDto());

        EntityModel<ResponsePost> entityModel = assembler.toModel(postDto);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<EntityModel<ResponsePost>> one(@PathVariable Long postId) {
        PostDto postDto = service.findById(postId);

        return ResponseEntity.ok(assembler.toModel(postDto));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponsePost>>> all() {
        List<PostDto> postDtos = service.listAll();

        return ResponseEntity.ok(assembler.toCollectionModel(postDtos));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<EntityModel<ResponsePost>> modify(
            @PathVariable Long postId, @RequestBody PostForm postForm) {
        PostDto postDto = postForm.toDto();
        postDto.setId(postId);

        PostDto modifiedPostDto = service.modify(postDto);

        if (modifiedPostDto.getId() != postId) {
            EntityModel<ResponsePost> entityModel = assembler.toModel(modifiedPostDto);

            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                    .body(entityModel);
        }

        return ResponseEntity.ok(assembler.toModel(modifiedPostDto));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<EntityModel<ResponsePost>> delete(@PathVariable Long postId) {

        if (!service.deleteById(postId)) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }
}
