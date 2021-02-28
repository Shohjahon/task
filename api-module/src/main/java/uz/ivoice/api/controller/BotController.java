package uz.ivoice.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.ivoice.api.common.BaseComponent;
import uz.ivoice.api.dto.ApiResponse;
import uz.ivoice.api.exception.CustomException;
import uz.ivoice.api.service.BotService;

import static uz.ivoice.api.constant.AppConstants.ASK_PARAM_DEFAULT;
import static uz.ivoice.api.constant.AppConstants.ASK_PARAM_NAME;

@RestController
@RequestMapping(value = "/api/v1/bot")
public class BotController extends BaseComponent {
    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping("/ask")
    public ResponseEntity<ApiResponse<?>> answer(@RequestParam(value = ASK_PARAM_NAME,
                                              defaultValue = ASK_PARAM_DEFAULT) String ask) throws Exception{
        ApiResponse<String> response = new ApiResponse<>();
        try {
            response.setData(botService.answer(ask));
            response.setCode(getSuccessCode());
            response.setMessage(getSuccessMessage());
        } catch (CustomException e){
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());
        }
        return buildResponse(response);
    }

}
