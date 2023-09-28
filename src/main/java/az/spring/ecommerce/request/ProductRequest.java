package az.spring.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;
    private String name;
    private Long price;
    private String material;
    private Long categoryId;

    private String imageOfProduct; //TODO: Check with this field

}