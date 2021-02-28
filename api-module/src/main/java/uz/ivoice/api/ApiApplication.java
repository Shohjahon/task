package uz.ivoice.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Slf4j
@PropertySources(value = {
        @PropertySource(value = "classpath:application.properties", encoding = "UTF-8"),
        @PropertySource(value = "classpath:error.properties", encoding = "UTF-8"),
})
@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


    @Override
    public void run(String... args) {
        log.info("Api service is started!");
    }
}
