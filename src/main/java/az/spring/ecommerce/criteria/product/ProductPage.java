package az.spring.ecommerce.criteria.product;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class ProductPage {

    private Integer pageNumber;
    private Integer pageSize;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy;

}
