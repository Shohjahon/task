package uz.ivoice.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BotApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BotApplication.class, args)));
    }

}
