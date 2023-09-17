package az.spring.ecommerce.criteria.product;

import az.spring.ecommerce.model.Product;
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

public class ProductCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Product> getProductsWithCriteria(ProductPage productPage, ProductSearchCriteria searchCriteria) {
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        Predicate predicate = getPredicates(searchCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(productPage, criteriaQuery, productRoot);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setMaxResults(productPage.getPageSize());
        typedQuery.setFirstResult(productPage.getPageNumber() * productPage.getPageSize());

        Pageable pageable = getPageable(productPage);
        long countProducts = getCountProducts(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, countProducts);
    }

    private Predicate getPredicates(ProductSearchCriteria searchCriteria, Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(productRoot.get("name"), "%" + searchCriteria.getName() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(productRoot.get("color"), "%" + searchCriteria.getColor() + "%"));
        }
        if (Objects.nonNull(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.equal(productRoot.get("price"), searchCriteria.getPrice()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(ProductPage productPage, CriteriaQuery<Product> criteriaQuery, Root<Product> productRoot) {
        if (productPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
        } else
            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
    }

    private Pageable getPageable(ProductPage productPage) {
        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
        return PageRequest.of(productPage.getPageNumber(), productPage.getPageSize(), sort);
    }

    private long getCountProducts(Predicate predicate) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.count(productRoot)).where(predicate);
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
