package az.spring.ecommerce.service;

import az.spring.ecommerce.enums.UserRole;
import az.spring.ecommerce.model.HeadCollection;
import az.spring.ecommerce.model.SubCollection;
import az.spring.ecommerce.model.User;
import az.spring.ecommerce.repository.HeadCollectionRepository;
import az.spring.ecommerce.repository.SubCollectionRepository;
import az.spring.ecommerce.repository.UserRepository;
import az.spring.ecommerce.request.SubCollectionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static az.spring.ecommerce.constant.CommerceConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
public class SubCollectionService {

    private final SubCollectionRepository subCollectionRepository;
    private final UserRepository userRepository;
    private final HeadCollectionRepository headCollectionRepository;

    public SubCollectionService(SubCollectionRepository subCollectionRepository, UserRepository userRepository, HeadCollectionRepository headCollectionRepository) {
        this.subCollectionRepository = subCollectionRepository;
        this.userRepository = userRepository;
        this.headCollectionRepository = headCollectionRepository;
    }

    public ResponseEntity<?> createSubCollection(SubCollectionRequest sub, Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent() && optionalUser.get().getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            SubCollection subColl = subCollectionRepository.findByName(sub.getName()).orElseGet(() -> null);

            if (Objects.isNull(subColl)) {
                HeadCollection headCollection = headCollectionRepository.findById(sub.getHeadCollectionId()).orElseGet(() -> null);
                if (Objects.nonNull(headCollection)) {
                    SubCollection subCollection = new SubCollection();
                    subCollection.setName(sub.getName());
                    subCollection.setHeadCollection(headCollection);
                    SubCollection saved = subCollectionRepository.save(subCollection);
                    return ResponseEntity.ok(saved);
                }
            return ResponseEntity.status(NOT_FOUND).body("Head Collection doesn't exist.");
            }
            return ResponseEntity.status(CONFLICT).body("This Sub Collection already exists..");

        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }

    public ResponseEntity<?> getSubCollectionById(Long id) {
        SubCollection sub = subCollectionRepository.findById(id).orElseGet(() -> null);
        if (Objects.nonNull(sub))
            return ResponseEntity.ok(sub);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sub Collection doesn't exist id: " + id);
    }

    public ResponseEntity<?> updateSubCollection(Long userId, SubCollectionRequest sub, Long subCollectionId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            SubCollection dbSubColl = subCollectionRepository.findById(subCollectionId).orElseGet(() -> null);
            if (Objects.nonNull(dbSubColl)) {
                HeadCollection dbHeadColl = headCollectionRepository.findById(sub.getHeadCollectionId()).orElseGet(() -> null);
                if (Objects.nonNull(dbHeadColl)) {
                    dbSubColl.setName(sub.getName());
                    dbSubColl.setHeadCollection(dbHeadColl);
                    dbSubColl.setId(subCollectionId);
                    return ResponseEntity.ok(subCollectionRepository.save(dbSubColl));
                }
                return ResponseEntity.status(NOT_FOUND).body(HEAD_COLLECTION_NOT_FOUND);
            }
            return ResponseEntity.status(NOT_FOUND).body(SUB_COLLECTION_NOT_FOUND);
        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }

    public ResponseEntity<?> getAllSubCollections() {
        return ResponseEntity.ok(subCollectionRepository.findAll());
    }

    public ResponseEntity<?> delete(Long subCollectionId, Long userId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user) && user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
            subCollectionRepository.deleteById(subCollectionId);
            return ResponseEntity.status(OK).body(SUCCESSFULLY_SUB_CATEGORY_DELETED);
        }
        return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
    }
}
