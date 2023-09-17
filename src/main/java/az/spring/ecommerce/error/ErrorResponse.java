package az.spring.ecommerce.error;

import lombok.Data;

@Data
public class ErrorResponse {

    private String code;
    private String message;

}