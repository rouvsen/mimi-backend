package az.spring.ecommerce.controller;

import az.spring.ecommerce.request.StatusAndRoleUserRequest;
import az.spring.ecommerce.request.UserSignUpRequest;
import az.spring.ecommerce.response.AuthenticationResponse;
import az.spring.ecommerce.service.UserService;
import az.spring.ecommerce.wrapper.UserWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
            return userService.signUp(userSignUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserSignUpRequest userSignUpRequest) {
        String jwt = userService.login(userSignUpRequest);
        if (jwt == null) {
            return ResponseEntity.status(BAD_REQUEST).build();
        } else {
            AuthenticationResponse response = new AuthenticationResponse();
            response.setJwtToken(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "userId") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get/username/{username}")
    public List<UserWrapper> getUserByFirstName(@PathVariable(name = "username") String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/get/name/{name}")
    public List<UserWrapper> getUserByName(@PathVariable(name = "name") String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/get/status/{status}")
    public List<UserWrapper> getUserByStatus(@PathVariable(name = "status") String status) {
        return userService.getUserByStatus(status);
    }

    @PostMapping("/updateStatusAndRole/{userId}")
    public ResponseEntity<?> updateStatusAndRole(
            @PathVariable Long userId,
            @RequestBody StatusAndRoleUserRequest updateStatusAndRoleUserRequest
    ) {
            return userService.updateStatusAndRole(userId, updateStatusAndRoleUserRequest);
    }

}