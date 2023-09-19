package az.spring.ecommerce.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;
    private String name;
    private Long price;
    private String material;
    private Long categoryId;

    private List<String> imagesOfProduct; //TODO: Check with this field

}