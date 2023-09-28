package az.spring.ecommerce.controller;

import az.spring.ecommerce.model.Category;
import az.spring.ecommerce.request.CategoryRequest;
import az.spring.ecommerce.request.FilterSearchRequest;
import az.spring.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/specification")
    public List<Category> getCategoriesWithSpecification(@RequestBody FilterSearchRequest filterSearchRequest) {
        return categoryService.getCategoriesWithSpecification(filterSearchRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest categoryRequest,
                                              @PathVariable(name = "userId") Long userId) {
            return categoryService.addCategory(categoryRequest, userId);
    }

    @GetMapping("/byId/{categoryId}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable("categoryId") Long id
    ) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{userId}/update/{categoryId}") //
    public ResponseEntity<?> updateCategory(@RequestBody CategoryRequest categoryRequest,
                                            @PathVariable(name = "userId") Long userId,
                                            @PathVariable(name = "categoryId") Long categoryId) {
            return categoryService.updateCategory(userId, categoryRequest, categoryId);
    }

    @DeleteMapping("/{userId}/delete/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "categoryId") Long categoryId) {
            return categoryService.delete(userId, categoryId);
    }

}