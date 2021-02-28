package uz.ivoice.bot.config;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import static uz.ivoice.bot.constants.BotConstants.AKKA_ACTOR_SYSTEM;

@Lazy
@Configuration
public class AkkConfig {
    private final ApplicationContext applicationContext;
    private final SpringExtension springExtension;

    public AkkConfig(ApplicationContext applicationContext,
                     SpringExtension springExtension) {
        this.applicationContext = applicationContext;
        this.springExtension = springExtension;
    }

    @Bean
    public ActorSystem actorSystem(){
        ActorSystem actorSystem = ActorSystem.create(AKKA_ACTOR_SYSTEM, akkaConfiguration());
        springExtension.initialize(applicationContext);
        return actorSystem;
    }

    @Bean
    public Config akkaConfiguration(){
        return ConfigFactory.load();
    }
}
