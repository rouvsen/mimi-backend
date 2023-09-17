package az.spring.ecommerce.criteria.product;

import lombok.Data;

@Data
public class ProductSearchCriteria {

    private String name;
    private String color;
    private Long price;

}
