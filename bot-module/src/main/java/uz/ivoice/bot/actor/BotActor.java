package uz.ivoice.bot.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uz.ivoice.bot.config.IntegrationProperties;
import uz.ivoice.bot.dto.ApiResponse;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static uz.ivoice.bot.util.Utils.checkForMapping;

@Component("bot_actor")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BotActor extends AbstractLoggingActor {
    private final RestTemplate restTemplate;
    private final IntegrationProperties properties;

    public BotActor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.properties = new IntegrationProperties();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(Question.class, this::onQuestion)
                .build();
    }

    private void onQuestion(Question question){
        log().debug("question: {}", question);
        ActorRef sender = getSender();
        Optional<ApiResponse<String>> responseOptional = ask(question.getQuestion());
        if (responseOptional.isPresent()){
            ApiResponse<String> response = responseOptional.get();
            Question result = response.isSuccess() ? new Question(response.getData()) :
                    new Question(response.getMessage());
            sender.tell(result, sender);
        }
    }

    private Optional<ApiResponse<String>> ask(String question){
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(properties.apiServiceUrl())
                    .queryParam("question", question)
                    .toUriString();

            Optional<ApiResponse<String>> response = Optional
                    .ofNullable(restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<>(headers()),
                            new ParameterizedTypeReference<ApiResponse<String>>() {}).getBody());

            if (response.isEmpty()){
                return response;
            }
            log().debug("response: {}", response.get().toString());
            return response;
        }catch (HttpServerErrorException | HttpClientErrorException ex) {
            log().error("client error: {}", ex.getMessage());
            return checkForMapping(ex.getResponseBodyAsString(),
                    new TypeReference<ApiResponse<String>>() {});
        } catch (ResourceAccessException ex) {
            log().error("time out: {}", ex.getMessage());
        } catch (Exception ex) {
            log().error("internal error: {}", ex.getMessage());
        }
        return Optional.empty();
    }

    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("accept-language", "en");
        return headers;
    }

    public static class Question{
        private String question;

        public Question(String question) {
            this.question = question;
        }

        public Question() {
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        @Override
        public String toString() {
            return "Question{" +
                    "question='" + question + '\'' +
                    '}';
        }
    }
}
