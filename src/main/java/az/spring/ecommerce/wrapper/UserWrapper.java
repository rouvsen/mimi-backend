package az.spring.ecommerce.wrapper;

import lombok.Data;

@Data
public class UserWrapper {

    private Long id;
    private String name;
    private String email;
    private String username;
    private String status;

    public UserWrapper(Long id, String name, String email, String username, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.username = username;
    }

}