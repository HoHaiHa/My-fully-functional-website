package springbootWeb2.com.hohaiha.app.mapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.AdminCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.AdminUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.User;

@Component
public class UserMapperImpl implements UserMapper {
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public User toUser(UserCreationRequest request) {
		return User.builder()
				.username(request.getUsername())
				.name(request.getName())
				.dob(request.getDob())
				.email(request.getEmail())
				.phone(request.getPhone())
				.build();
	}

	
	@Override
	public User adminToUser(AdminCreationRequest request) {
		return User.builder()
				.username(request.getUsername())
				.name(request.getName())
				.dob(request.getDob())
				.email(request.getEmail())
				.phone(request.getPhone())
				.build();
	}

	@Override
	public UserResponse toUserResponse(User user) {
		return UserResponse.builder()
				.id(user.getId())
				.username(user.getUsername())
				.name(user.getName())
				.dob(user.getDob())
				.email(user.getEmail())
				.phone(user.getPhone())
				.roles(new HashSet<>(user.getRoles().stream()
						.map(role -> roleMapper.toRoleResponse(role)).collect(Collectors.toSet())))
				.cartId(user.getCart().getId())
				.build();
	}

	@Override
	public User updateUser(User user, UserUpdateRequest request) {
		user.setName(request.getName());
		user.setDob(request.getDob());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		
		return user;
	}
	
	@Override
	public User AdminUpdateUser(User user, AdminUpdateRequest request) {
		user.setName(request.getName());
		user.setDob(request.getDob());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		
		return user;
	}



}
