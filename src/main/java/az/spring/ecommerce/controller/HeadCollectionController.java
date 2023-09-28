package az.spring.ecommerce.controller;

import az.spring.ecommerce.model.HeadCollection;
import az.spring.ecommerce.service.HeadCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/headCollections")
public class HeadCollectionController {

    private final HeadCollectionService headCollectionService;

    public HeadCollectionController(HeadCollectionService headCollectionService) {
        this.headCollectionService = headCollectionService;
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> createHeadCollection(
            @RequestBody HeadCollection head,
            @PathVariable Long userId
    ) {
        return headCollectionService.createHeadCollection(head, userId);
    }

    @GetMapping("/byId/{headCollectionId}")
    public ResponseEntity<?> getHeadCollectionById(@PathVariable("headCollectionId") Long id) {
        return headCollectionService.getHeadCollectionById(id);
    }

    @PutMapping("/{userId}/update/{headCollectionId}")
    public ResponseEntity<?> updateHeadCollection(
            @PathVariable("userId") Long userId,
            @RequestBody HeadCollection head,
            @PathVariable("headCollectionId") Long headCollectionId
    ) {
        return headCollectionService.updateHeadCollection(userId, head, headCollectionId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllHeadCollections() {
        return headCollectionService.getAllHeadCollections();
    }

    @DeleteMapping("/{headCollectionId}/delete/{userId}")
    public ResponseEntity<?> deleteHeadCollection(
            @PathVariable(name = "headCollectionId") Long headCollectionId,
            @PathVariable(name = "userId") Long userId) {
        return headCollectionService.delete(headCollectionId, userId);
    }
}
