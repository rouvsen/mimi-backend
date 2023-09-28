package az.spring.ecommerce.controller;

import az.spring.ecommerce.model.SubCollection;
import az.spring.ecommerce.request.SubCollectionRequest;
import az.spring.ecommerce.service.SubCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subCollections")
public class SubCollectionController {

    private final SubCollectionService subCollectionService;

    public SubCollectionController(SubCollectionService subCollectionService) {
        this.subCollectionService = subCollectionService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> createSubCollection(
            @RequestBody SubCollectionRequest sub,
            @PathVariable Long userId
    ) {
        return subCollectionService.createSubCollection(sub, userId);
    }

    @GetMapping("/byId/{subCollectionId}")
    public ResponseEntity<?> getSubCollectionById(@PathVariable("subCollectionId") Long id) {
        return subCollectionService.getSubCollectionById(id);
    }

    @PutMapping("/{userId}/update/{subCollectionId}")
    public ResponseEntity<?> updateSubCollection(
            @PathVariable("userId") Long userId,
            @RequestBody SubCollectionRequest head,
            @PathVariable("subCollectionId") Long subCollectionId
    ) {
        return subCollectionService.updateSubCollection(userId, head, subCollectionId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubCollections() {
        return subCollectionService.getAllSubCollections();
    }

    @DeleteMapping("/{subCollectionId}/delete/{userId}")
    public ResponseEntity<?> deleteSubCollection(
            @PathVariable(name = "subCollectionId") Long subCollectionId,
            @PathVariable(name = "userId") Long userId) {
        return subCollectionService.delete(subCollectionId, userId);
    }
}
