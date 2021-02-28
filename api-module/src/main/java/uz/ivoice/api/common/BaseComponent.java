package uz.ivoice.api.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.ivoice.api.dto.ApiResponse;

@Component
public class BaseComponent {
    @Autowired
    private Environment env;
    @Autowired
    private MessageSource messageSource;


    public Integer getCode(String key){
        return env.getProperty(key, Integer.class);
    }

    public String getMessage(String key){
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    protected Integer getInternalErrorCode(){
        return getCode("bot.internal.error.code");
    }

    protected String getInternalErrorMessage(){
        return getMessage("bot.internal.error");
    }

    protected Integer getSuccessCode(){
        return getCode("bot.success");
    }

    protected String getSuccessMessage(){
        return getMessage("bot.success");
    }

    protected ResponseEntity<ApiResponse<?>> buildResponse(ApiResponse<?> response){
        return response.isSuccess() ? ResponseEntity.status(HttpStatus.OK).body(response) :
               ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    public ResponseEntity<ApiResponse<?>> internalServerError(){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse
                        .builder()
                        .code(getInternalErrorCode())
                        .message(getInternalErrorMessage())
                        .build());
    }
}
