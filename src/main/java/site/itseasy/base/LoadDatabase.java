package site.itseasy.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import site.itseasy.blog.post.dto.PostDto;
import site.itseasy.blog.post.service.PostService;

import java.util.stream.IntStream;

@Slf4j
@Configuration
@Profile({"local", "dev"})
class LoadDatabase {
    @Bean
    CommandLineRunner initDatabasePost(@Qualifier("postServiceImpl") PostService service) {
        return args -> {
            IntStream.rangeClosed(1, 10).forEach(no ->
                    service.register(
                            new PostDto(
                                    "제목%d".formatted(no),
                                    "내용%d".formatted(no)
                            )
                    )
            );
        };
    }
}
