package uz.ivoice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    /**
     *  code for representation of operation status
     */
    @JsonProperty("code")
    private Integer code;
    /**
     *  description of operation status
     */
    @JsonProperty("message")
    private String message;
    /**
     *  response body
     */
    @JsonProperty("data")
    private T data;

    @JsonIgnore
    public boolean isSuccess(){
        return code == 0;
    }
}
