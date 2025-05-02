package taek.servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class web {

    @GetMapping("/")
    public String test(){
        return "Hello World";
    }
}
