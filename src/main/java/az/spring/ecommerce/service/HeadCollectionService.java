package az.spring.ecommerce.service;

import az.spring.ecommerce.enums.UserRole;
import az.spring.ecommerce.model.HeadCollection;
import az.spring.ecommerce.model.User;
import az.spring.ecommerce.repository.HeadCollectionRepository;
import az.spring.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static az.spring.ecommerce.constant.CommerceConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
public class HeadCollectionService {

    private final HeadCollectionRepository headCollectionRepository;
    private final UserRepository userRepository;

    public HeadCollectionService(HeadCollectionRepository headCollectionRepository, UserRepository userRepository) {
        this.headCollectionRepository = headCollectionRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createHeadCollection(HeadCollection head, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent() && optionalUser.get().getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            HeadCollection isPresentHead = headCollectionRepository.findByName(head.getName()).orElseGet(() -> null);
            if (Objects.isNull(isPresentHead)) {
                HeadCollection saved = headCollectionRepository.save(head);
                return ResponseEntity.ok(saved);
            }
            return ResponseEntity.status(CONFLICT).body("This Head Collection already exists..");
        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }


    public ResponseEntity<?> getHeadCollectionById(Long id) {
        HeadCollection head = headCollectionRepository.findById(id).orElseGet(() -> null);
        if (Objects.nonNull(head))
            return ResponseEntity.ok(head);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Head Collection doesn't exist id: " + id);
    }

    public ResponseEntity<?> updateHeadCollection(Long userId, HeadCollection head, Long headCollectionId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            HeadCollection dbHead = headCollectionRepository.findById(headCollectionId).orElseGet(() -> null);
            if (Objects.nonNull(dbHead)) {
                head.setId(headCollectionId);
                return ResponseEntity.ok(headCollectionRepository.save(head));
            }
            return ResponseEntity.status(NOT_FOUND).body(HEAD_COLLECTION_NOT_FOUND);
        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }

    public ResponseEntity<?> getAllHeadCollections() {
        return ResponseEntity.ok(headCollectionRepository.findAll());
    }

    public ResponseEntity<?> delete(Long headCollectionId, Long userId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            headCollectionRepository.deleteById(headCollectionId);
            return ResponseEntity.status(OK).body(SUCCESSFULLY_HEAD_CATEGORY_DELETED);
        } else
            return ResponseEntity.status(UNAUTHORIZED).build();
    }
}
