package site.itseasy;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class TestController {

    private static final String TEMPLATE = "Hello, %s!";
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        Greeting greeting = new Greeting(TEMPLATE.formatted(name));

        greeting.add(linkTo(
                methodOn(TestController.class)
                        .greeting(name))
                        .withSelfRel()
        );

        return new ResponseEntity<>(greeting, OK);
    }
}
