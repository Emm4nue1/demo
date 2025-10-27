package demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"demo", "demo.service", "demo.repository", "demo.controller"})
@EntityScan(basePackages = {"model"})
@EnableJpaRepositories(basePackages = {"demo.repository"})

public class GestionEnviosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionEnviosApplication.class, args);
    }
}

