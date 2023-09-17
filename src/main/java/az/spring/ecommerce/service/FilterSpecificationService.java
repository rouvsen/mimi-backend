package az.spring.ecommerce.service;

import az.spring.ecommerce.request.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilterSpecificationService<T> {

    public Specification<T> getObjectsWithSpecification(SearchRequest searchRequest) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(searchRequest.getColumn()), "%" + searchRequest.getValue() + "%");
    }

}
