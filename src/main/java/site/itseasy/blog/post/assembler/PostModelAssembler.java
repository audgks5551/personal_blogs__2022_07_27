package site.itseasy.blog.post.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import site.itseasy.blog.post.controller.PostController;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.response.ResponsePost;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<PostDto, EntityModel<ResponsePost>> {
    @Override
    public EntityModel<ResponsePost> toModel(PostDto postDto) {
        ResponsePost responsePost = postDto.toVo(ResponsePost.class);

        return EntityModel.of(responsePost,
                linkTo(methodOn(PostController.class).one(responsePost.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).all()).withRel("posts")
        );
    }

    @Override
    public CollectionModel<EntityModel<ResponsePost>> toCollectionModel(Iterable<? extends PostDto> postDtos) {
        List<EntityModel<ResponsePost>> responsePosts =
                StreamSupport.stream(postDtos.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(responsePosts,
                linkTo(methodOn(PostController.class).all()).withSelfRel()
        );
    }
}
