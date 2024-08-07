package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.AdminCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.AdminUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.User;

@Component
public interface UserMapper {
    public User toUser(UserCreationRequest request);
    
    public User adminToUser(AdminCreationRequest request);

    public UserResponse toUserResponse(User user);

    public User updateUser(User user, UserUpdateRequest request);
    
    public User AdminUpdateUser(User user, AdminUpdateRequest request);

}
