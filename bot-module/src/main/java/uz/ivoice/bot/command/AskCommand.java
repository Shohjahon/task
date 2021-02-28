package uz.ivoice.bot.command;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import uz.ivoice.bot.actor.BotActor;
import uz.ivoice.bot.config.SpringExtension;
import uz.ivoice.bot.util.Utils;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static uz.ivoice.bot.constants.BotConstants.ASK_COMMAND;

@Slf4j
@Component
@CommandLine.Command(name = ASK_COMMAND,
                     description = "Asks question from Api",
                     mixinStandardHelpOptions = true,
                     version = "v1.0.0")
public class AskCommand implements Callable<Integer>{
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    private SpringExtension extension;

    @CommandLine.Option(names = {"-q", "--question"}, description = "question to be answered", defaultValue = "hello!")
    String question;

    @Override
    public Integer call() throws Exception {
       if (Objects.isNull(question) || question.isEmpty()){
           Utils.printf("@|bold,red, ****** Question (`-q`) must not be empty ****** |@\n");
           return CommandLine.ExitCode.USAGE;
       }

       BotActor.Question question = new BotActor.Question(this.question);
       String answer= getAnswerFromActor(question);
       Utils.printf("Answer: %s\n", answer);
       return CommandLine.ExitCode.OK;
    }

    private String getAnswerFromActor(BotActor.Question question){

        final ActorRef actor = actorSystem.actorOf(extension.props("bot_actor"), "bot_actor");
        actor.tell(question, ActorRef.noSender());
        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        Future<Object> awaitable = Patterns.ask(actor, question, Timeout.durationToTimeout(duration));
        try {
            BotActor.Question response = (BotActor.Question) Await.result(awaitable, duration);
            log.debug("response: {}", response);
            return response.getQuestion();
        } catch (Exception e) {
            log.error("error response: {}", e.getMessage());
        }
        return null;
    }

}
