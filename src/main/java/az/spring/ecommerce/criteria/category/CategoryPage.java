package az.spring.ecommerce.criteria.category;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class CategoryPage {

    private Integer pageNumber;
    private Integer pageSize;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy;

}
