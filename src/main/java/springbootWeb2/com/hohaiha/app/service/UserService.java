package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import springbootWeb2.com.hohaiha.app.dto.request.AdminCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.AdminUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PageRespose;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.User;

@Service
public interface UserService {
    public UserResponse createUser(UserCreationRequest request);

    public UserResponse getMyInfo();
     
    public UserResponse updateUser(String userId, UserUpdateRequest request);

    public void deleteUser(String userId);

    public List<UserResponse> getUsers(int page, int size);

    public UserResponse getUser(String id);
    
    public PageRespose getPage(int page,int size);

	public UserResponse adminUpdateUser(String userId, AdminUpdateRequest request);

	public UserResponse AdminCreateUser(AdminCreationRequest request);
	
	public List<UserResponse> getUserByKeyword(String keyword);
}
