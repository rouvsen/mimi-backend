package az.spring.ecommerce.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;

}