package az.spring.ecommerce.request;

import lombok.Data;

@Data
public class StatusAndRoleUserRequest {
    private Long id;
    private String status;
    private String role;
}
