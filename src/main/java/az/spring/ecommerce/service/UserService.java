package az.spring.ecommerce.service;

import az.spring.ecommerce.constant.CommerceConstant;
import az.spring.ecommerce.enums.UserRole;
import az.spring.ecommerce.mappers.UserMapper;
import az.spring.ecommerce.model.User;
import az.spring.ecommerce.repository.UserRepository;
import az.spring.ecommerce.request.StatusAndRoleUserRequest;
import az.spring.ecommerce.request.UserSignUpRequest;
import az.spring.ecommerce.security.JwtUtil;
import az.spring.ecommerce.utils.CommerceUtil;
import az.spring.ecommerce.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static az.spring.ecommerce.constant.CommerceConstant.*;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final EncryptionService encryptionService;

    public ResponseEntity<?> signUp(UserSignUpRequest userSignUpRequest) {
            if (validationSignUp(userSignUpRequest)) {
                User user = userRepository.findFirstByEmail(userSignUpRequest.getEmail());
                if (Objects.isNull(user)) {
                    User saved = userRepository.save(getUserFromRequest(userSignUpRequest, false));
                    return ResponseEntity.ok(saved);
                } else {
                    return ResponseEntity.status(BAD_REQUEST).body(USER_ALREADY_EXISTS);
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
    }

    public String login(UserSignUpRequest userSignUpRequest) {
        Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(userSignUpRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encryptionService.verifyPassword(userSignUpRequest.getPassword(), user.getPassword())) {
                if (user.getStatus().equals("false"))
                    return ADMIN_APPROVAL;
                return jwtUtil.generateToken(userSignUpRequest.getEmail(), userSignUpRequest.getUserRole());
            } else
                return INVALID_DATA;
        }
        return CommerceConstant.BAD_CREDENTIALS;
    }

    public ResponseEntity<?> updateStatusAndRole(Long userId, StatusAndRoleUserRequest statusAndRoleUserRequest) {
            User user = userRepository.findById(userId).orElseGet(() -> null);
            if (Objects.nonNull(user)) {
                if (user.getUserRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
                    Optional<User> optionalUser = userRepository.findById(statusAndRoleUserRequest.getId());
                    if (optionalUser.isPresent()) {
                        User dbUser = optionalUser.get();
                        if (Objects.nonNull(statusAndRoleUserRequest.getRole())) {
                            dbUser.setUserRole(statusAndRoleUserRequest.getRole());
                        }
                        if (Objects.nonNull(statusAndRoleUserRequest.getStatus())) {
                            dbUser.setStatus(statusAndRoleUserRequest.getStatus());
                        }
                        return ResponseEntity.ok(userRepository.save(dbUser));
                    } else {
                        return ResponseEntity.status(NOT_FOUND).body(USER_NOT_FOUND);
                    }
                }
                return ResponseEntity.status(UNAUTHORIZED).body(UNAUTHORIZED_ACCESS);
            }

            return ResponseEntity.status(NOT_FOUND).body("User not found that entered Admin Id");
    }

    public ResponseEntity<?> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseGet(() -> null);
        if (Objects.nonNull(user)) {
            return ResponseEntity.ok(userMapper.fromModelToResponse(user));
        }
        return ResponseEntity.status(NOT_FOUND).body(USER_NOT_FOUND);
    }

    public List<UserWrapper> getUserByUsername(String username) {
        return userMapper.fromModelToWrapper(userRepository.findByUsername(username));
    }

    public List<UserWrapper> getUserByName(String name) {
        return userMapper.fromModelToWrapper(userRepository.findByName(name));
    }

    public List<UserWrapper> getUserByStatus(String status) {
        return userMapper.fromModelToWrapper(userRepository.findByStatus(status));
    }

    private boolean validationSignUp(UserSignUpRequest signUpRequest) {
        return signUpRequest.getName() != null && signUpRequest.getEmail() != null
                && signUpRequest.getUsername() != null && signUpRequest.getPassword() != null;
    }

    private User getUserFromRequest(UserSignUpRequest userSignUpRequest, boolean isAdd) {
        User user = new User();
        if (isAdd) {
            user.setId(userSignUpRequest.getId());
        }
        user.setUsername(userSignUpRequest.getUsername());
        user.setEmail(userSignUpRequest.getEmail());
        user.setName(userSignUpRequest.getName());
        user.setPassword(encryptionService.encryptPassword(userSignUpRequest.getPassword()));
        user.setUserRole("moderator");
        user.setStatus("false");
        return user;
    }

}