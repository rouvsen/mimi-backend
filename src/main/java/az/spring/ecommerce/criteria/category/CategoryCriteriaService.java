package az.spring.ecommerce.criteria.category;

import az.spring.ecommerce.model.Category;
import az.spring.ecommerce.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCriteriaService {

    private final CategoryCriteriaRepository criteriaRepository;

    public Page<Category> getCategoryWithCriteria(CategoryPage categoryPage, CategorySearchCriteria searchCriteria) {
        return criteriaRepository.getCategoriesWithCriteria(categoryPage, searchCriteria);
    }

}
