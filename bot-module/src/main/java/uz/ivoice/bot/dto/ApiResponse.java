package uz.ivoice.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    /**
     *  code for representation of operation status
     */
    private Integer code;
    /**
     *  description of operation status
     */
    private String message;
    /**
     *  response body
     */
    private T data;

    public boolean isSuccess(){
        return code == 0;
    }
}
