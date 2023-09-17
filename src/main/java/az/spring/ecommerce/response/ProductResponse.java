package az.spring.ecommerce.response;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private Long price;
    private String color;
    private String material;
    private Long categoryId;

}