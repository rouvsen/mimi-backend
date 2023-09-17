package az.spring.ecommerce.criteria.category;

import az.spring.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryCriteriaController {

    private final CategoryCriteriaService criteriaService;

    @GetMapping("/criteria")
    public ResponseEntity<Page<Category>> getCategoryWithCriteria(CategoryPage categoryPage, CategorySearchCriteria searchCriteria) {
        return ResponseEntity.ok(criteriaService.getCategoryWithCriteria(categoryPage, searchCriteria));
    }

}
