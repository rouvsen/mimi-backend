package az.spring.ecommerce.criteria.product;

import az.spring.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCriteriaController {

    private final ProductCriteriaService criteriaService;

    @GetMapping("/criteria")
    public ResponseEntity<Page<Product>> getProductWithCriteria(ProductPage productPage, ProductSearchCriteria searchCriteria) {
        return ResponseEntity.ok(criteriaService.getProductWithCriteria(productPage, searchCriteria));
    }

}
