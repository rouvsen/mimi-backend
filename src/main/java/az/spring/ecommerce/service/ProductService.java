package az.spring.ecommerce.service;

import az.spring.ecommerce.constant.CommerceConstant;
import az.spring.ecommerce.enums.UserRole;
import az.spring.ecommerce.mappers.ProductMapper;
import az.spring.ecommerce.model.Category;
import az.spring.ecommerce.model.Product;
import az.spring.ecommerce.model.User;
import az.spring.ecommerce.repository.CategoryRepository;
import az.spring.ecommerce.repository.ProductRepository;
import az.spring.ecommerce.repository.UserRepository;
import az.spring.ecommerce.request.FilterSearchRequest;
import az.spring.ecommerce.request.ProductRequest;
import az.spring.ecommerce.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static az.spring.ecommerce.constant.CommerceConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final FilterSpecificationService<Product> filterSpecificationService;
    private final ProductMapper productMapper;

    public List<Product> getProductsWithSpecification(FilterSearchRequest filterSearchRequest) {
        Specification<Product> productSpecification =
                filterSpecificationService.getObjectsWithSpecification(filterSearchRequest.getSearchRequest());
        return productRepository.findAll(productSpecification);
    }

    public ResponseEntity<?> createProduct(ProductRequest request, Long adminId) {
        Long catId = request.getCategoryId();
        Category cat = categoryRepository.findById(catId).orElseGet(() -> null);

        Product product = productMapper.fromRequestToModel(request);
        if (cat != null) {
            product.setCategory(cat);
        }
        User user = userRepository.findById(adminId).orElseGet(() -> null);
        if (user != null && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            Product saved = productRepository.save(product);
            ProductResponse productResponse = productMapper.fromModelToResponse(saved);
            if (cat != null) {
                productResponse.setCategoryId(cat.getId());
                return ResponseEntity.ok(productResponse);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Will be added Category doesn't exist!!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommerceConstant.UNAUTHORIZED_ACCESS);
    }

    public ResponseEntity<?> updateProduct(Long userId, ProductRequest request, Long productId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            Product product = productRepository.findById(productId).orElseGet(() -> null);
            Category category = categoryRepository.findById(request.getCategoryId()).orElseGet(() -> null);
            if (Objects.nonNull(product)) {
                if (Objects.nonNull(category)) {
                    Product updated = productMapper.fromRequestToModel(request);
                    updated.setId(productId);
                    updated.setCategory(category);
                    return ResponseEntity.ok(productRepository.save(updated));
                } else {
                    return ResponseEntity.status(NOT_FOUND).body("Category doesn't exist: " + request.getCategoryId());
                }
            }
            return ResponseEntity.status(NOT_FOUND).body("Product doesn't exist: " + productId);
        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED);
    }

    public ResponseEntity<?> getAllProducts() {
        List<Product> all = productRepository.findAll();
        List<ProductResponse> prosResponseList = new ArrayList<>();
        for (Product prod : all) {
            ProductResponse res = productMapper.fromModelToResponse(prod);
            res.setCategoryId(prod.getCategory().getId());
            prosResponseList.add(res);
        }
        return ResponseEntity.ok(prosResponseList);
    }

    public ResponseEntity<?> getProductById(Long id) {
        Product product = productRepository.findById(id).orElseGet(() -> null);
        if (Objects.nonNull(product))
            return ResponseEntity.ok(product);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product doesn't exist id: " + id);
    }

    public ResponseEntity<?> delete(Long productId, Long userId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            productRepository.deleteById(productId);
            return ResponseEntity.status(OK).body(SUCCESSFULLY_PRODUCT_DELETED);
        } else
            return ResponseEntity.status(UNAUTHORIZED).build();
    }
}