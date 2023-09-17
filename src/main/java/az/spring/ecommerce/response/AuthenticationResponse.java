package az.spring.ecommerce.response;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwtToken;

}