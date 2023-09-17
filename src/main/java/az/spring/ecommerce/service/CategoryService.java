package az.spring.ecommerce.service;

import az.spring.ecommerce.constant.CommerceConstant;
import az.spring.ecommerce.enums.UserRole;
import az.spring.ecommerce.model.Category;
import az.spring.ecommerce.model.SubCollection;
import az.spring.ecommerce.model.User;
import az.spring.ecommerce.repository.CategoryRepository;
import az.spring.ecommerce.repository.SubCollectionRepository;
import az.spring.ecommerce.repository.UserRepository;
import az.spring.ecommerce.request.CategoryRequest;
import az.spring.ecommerce.request.FilterSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static az.spring.ecommerce.constant.CommerceConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SubCollectionRepository subCollectionRepository;
    private final FilterSpecificationService<Category> filterSpecificationService;

    public List<Category> getCategoriesWithSpecification(FilterSearchRequest searchRequest) {
        Specification<Category> objectsWithSpecification = filterSpecificationService.getObjectsWithSpecification(searchRequest.getSearchRequest());
        return categoryRepository.findAll(objectsWithSpecification);
    }

    public ResponseEntity<?> addCategory(CategoryRequest categoryRequest, Long userId) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent() && optionalUser.get().getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
                if (validateCategory(categoryRequest, false)) {
                    Category dbCat = categoryRepository.findByName(categoryRequest.getName()).orElseGet(() -> null);
                    if (Objects.isNull(dbCat)) {
                        SubCollection subCollection = subCollectionRepository.findById(categoryRequest.getSubCollectionId()).orElseGet(() -> null);
                        if (Objects.nonNull(subCollection)) {
                            Category category = new Category();
                            category.setSubCollection(subCollection);
                            category.setName(categoryRequest.getName());
                            Category saved = categoryRepository.save(category);
                            return ResponseEntity.ok(saved);
                        }
                        return ResponseEntity.status(NOT_FOUND).body(SUB_COLLECTION_NOT_FOUND);
                    }
                    return ResponseEntity.status(CONFLICT).body("This Category already exists");
                } else
                    return ResponseEntity.status(BAD_REQUEST).build();
            }
            return ResponseEntity.status(UNAUTHORIZED).build();
    }

    public ResponseEntity<?> updateCategory(Long userId, CategoryRequest categoryRequest, Long categoryId) {
            User user = userRepository.findById(userId).orElseGet(() -> null);
            if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
                Category dbCat = categoryRepository.findById(categoryId).orElseGet(() -> null);
                if (Objects.nonNull(dbCat)) {
                    SubCollection subColl = subCollectionRepository.findById(categoryRequest.getSubCollectionId()).orElseGet(() -> null);
                    if (Objects.nonNull(subColl)) {
                        dbCat.setId(categoryId);
                        dbCat.setName(categoryRequest.getName());
                        dbCat.setSubCollection(subColl);
                        Category saved = categoryRepository.save(dbCat);
                        return ResponseEntity.ok(saved);
                    }
                    return ResponseEntity.status(NOT_FOUND).body(SUB_COLLECTION_NOT_FOUND);
                }
                return ResponseEntity.status(NOT_FOUND).body(CATEGORY_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }

    public ResponseEntity<?> delete(Long userId, Long categoryId) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent() && optionalUser.get().getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
                categoryRepository.deleteById(categoryId);
                return ResponseEntity.status(OK).body(SUCCESSFULLY_CATEGORY_DELETED);
            } else
                return ResponseEntity.status(UNAUTHORIZED).build();
    }

    private Category getCategoryFromRequest(CategoryRequest categoryRequest, Boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(categoryRequest.getId());
        }
        category.setName(categoryRequest.getName());
        return category;
    }

    private boolean validateCategory(CategoryRequest categoryRequest, boolean validateId) {
        if (categoryRequest.getName() != null) {
            if (categoryRequest.getId() != null && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }


    public List<Category> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    public ResponseEntity<?> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseGet(() -> null);
        if (Objects.nonNull(category))
            return ResponseEntity.ok(category);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CATEGORY_NOT_FOUND);
    }
}