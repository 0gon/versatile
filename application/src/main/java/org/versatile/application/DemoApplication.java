package org.versatile.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.versatile.library.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication(scanBasePackageClasses = { MyService.class })
@RestController
public class DemoApplication {

    private final MyService myService;

    public DemoApplication(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/")
    public String home() {
        return myService.message();
    }

    @GetMapping("/set")
    public void set(@RequestParam String message) {
        myService.setMessage(message);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}