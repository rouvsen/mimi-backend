package az.spring.ecommerce.criteria.product;

import az.spring.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCriteriaService {

    private final ProductCriteriaRepository criteriaRepository;

    public Page<Product> getProductWithCriteria(ProductPage productPage, ProductSearchCriteria searchCriteria) {
        return criteriaRepository.getProductsWithCriteria(productPage, searchCriteria);
    }

}
