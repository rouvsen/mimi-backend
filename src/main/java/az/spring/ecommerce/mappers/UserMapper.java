package az.spring.ecommerce.mappers;

import az.spring.ecommerce.model.User;
import az.spring.ecommerce.request.UserRequest;
import az.spring.ecommerce.request.UserSignUpRequest;
import az.spring.ecommerce.response.UserResponse;
import az.spring.ecommerce.wrapper.UserWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User fromUserSignUpRequestToModel(UserSignUpRequest userSignUpRequest);

    UserRequest fromModelToUserRequest(User user);

    List<UserWrapper> fromModelToWrapper(List<User> users);

    UserResponse fromModelToResponse(User user);

}