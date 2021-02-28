package uz.ivoice.api.exception;

public class CustomException extends Exception{
    private Integer code;

    public CustomException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
