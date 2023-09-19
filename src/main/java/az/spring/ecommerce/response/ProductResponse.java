package az.spring.ecommerce.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private Long price;
    private String material;
    private Long categoryId;

    private List<String> imagesOfProduct; //TODO: Check with this field

}