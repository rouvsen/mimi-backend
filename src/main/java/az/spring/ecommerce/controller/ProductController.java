package az.spring.ecommerce.controller;

import az.spring.ecommerce.model.Product;
import az.spring.ecommerce.request.FilterSearchRequest;
import az.spring.ecommerce.request.ProductRequest;
import az.spring.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/specification")
    public List<Product> getProductsWithSpecification(@RequestBody FilterSearchRequest filterSearchRequest) {
        return productService.getProductsWithSpecification(filterSearchRequest);
    }

    @PostMapping("/add/{adminId}")
    public ResponseEntity<?> createProduct(
            @ModelAttribute ProductRequest request, //TODO: CHECK API
            @PathVariable Long adminId
    ) {
        return productService.createProduct(request, adminId);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{userId}/update/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("userId") Long userId,
            @ModelAttribute ProductRequest request, //TODO: CHECK API
            @PathVariable("productId") Long productId
    ) {
        return productService.updateProduct(userId, request, productId);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}/delete/{userId}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "productId") Long productId,
                                           @PathVariable(name = "userId") Long userId) {
        return productService.delete(productId, userId);
    }

}