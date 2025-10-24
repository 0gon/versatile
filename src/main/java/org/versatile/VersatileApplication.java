package org.versatile;

import org.gon.security.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"org.gon.security"})
@EnableJpaRepositories
@EntityScan(basePackages = {"org.gon.security"})
public class VersatileApplication {

    public static void main(String[] args) {
        Test.action();
        SpringApplication.run(VersatileApplication.class, args);
    }

}
 