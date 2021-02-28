package uz.ivoice.api.service.impl;

import org.springframework.stereotype.Service;
import uz.ivoice.api.common.BaseComponent;
import uz.ivoice.api.exception.CustomException;
import uz.ivoice.api.service.BotService;

@Service
public class BotServiceImpl extends BaseComponent implements BotService {
    @Override
    public String answer(String question) throws Exception {
        String message = String.format(getMessage("bot.is.not.ready"), question);
        throw new CustomException(message, getCode("bot.is.not.ready.code"));
    }
}
