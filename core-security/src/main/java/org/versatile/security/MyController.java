package org.versatile.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/error")
    public String getMethodName() {
        return "error!!";
    }
}
