package az.spring.ecommerce.request;

import lombok.Data;

@Data
public class SubCollectionRequest {
    private Long id;
    private String name;
    private Long headCollectionId;
}
