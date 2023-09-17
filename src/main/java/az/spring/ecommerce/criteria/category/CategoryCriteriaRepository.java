package az.spring.ecommerce.criteria.category;

import az.spring.ecommerce.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository

public class CategoryCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CategoryCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Category> getCategoriesWithCriteria(CategoryPage categoryPage, CategorySearchCriteria searchCriteria) {
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        Predicate predicate = getPredicates(searchCriteria, categoryRoot);
        criteriaQuery.where(predicate);
        setOrder(categoryPage, criteriaQuery, categoryRoot);

        TypedQuery<Category> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(categoryPage.getPageSize());
        typedQuery.setFirstResult(categoryPage.getPageNumber() * categoryPage.getPageSize());

        Pageable pageable = getPageable(categoryPage);
        long countCategories = getCountCategories(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, countCategories);
    }

    private Predicate getPredicates(CategorySearchCriteria searchCriteria, Root<Category> categoryRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(categoryRoot.get("name"), "%" + searchCriteria.getName() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(CategoryPage categoryPage, CriteriaQuery<Category> criteriaQuery, Root<Category> categoryRoot) {
        if (categoryPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(categoryRoot.get(categoryPage.getSortBy())));
        } else
            criteriaQuery.orderBy(criteriaBuilder.desc(categoryRoot.get(categoryPage.getSortBy())));
    }

    private Pageable getPageable(CategoryPage categoryPage) {
        Sort sort = Sort.by(categoryPage.getSortDirection(), categoryPage.getSortBy());
        return PageRequest.of(categoryPage.getPageNumber(), categoryPage.getPageSize(), sort);
    }

    private long getCountCategories(Predicate predicate) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        criteriaQuery.select(criteriaBuilder.count(categoryRoot)).where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
