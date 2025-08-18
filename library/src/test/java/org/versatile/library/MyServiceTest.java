package org.versatile.library;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.versatile.library.MyService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("service.message=Hello")
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        assertThat(myService.message()).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {
        // This class is used to bootstrap the Spring context for testing.
    }
}
