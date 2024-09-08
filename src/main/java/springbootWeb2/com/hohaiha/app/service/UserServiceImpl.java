package springbootWeb2.com.hohaiha.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.AdminCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.AdminUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PageRespose;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.Cart;
import springbootWeb2.com.hohaiha.app.entity.Role;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.mapper.PageMapper;
import springbootWeb2.com.hohaiha.app.mapper.UserMapper;
import springbootWeb2.com.hohaiha.app.repository.RoleRepository;
import springbootWeb2.com.hohaiha.app.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PageMapper pageMapper;
	

	// private PasswordEncoder passwordEncoder;

	public UserResponse createUser(UserCreationRequest request) {
		User user = userMapper.toUser(request);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		Role role = Role.builder().name("USER").description("user role").build();

		user.setRoles(new HashSet<>(Arrays.asList(role)));
		
		Cart cart = new Cart();
		
		user.setCart(cart);
		
		try {
			user = userRepository.save(user);
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}
		
		

		return userMapper.toUserResponse(user);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserResponse AdminCreateUser(AdminCreationRequest request) {
		User user = userMapper.adminToUser(request);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		var roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));
		
		Cart cart = new Cart();
		user.setCart(cart);

		try {
			user = userRepository.save(user);
		} catch (DataIntegrityViolationException exception) {
			throw new AppException(ErrorCode.USER_EXISTED);
		}

		return userMapper.toUserResponse(user);
	}

	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();

		User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		return userMapper.toUserResponse(user);
	}

	@PostAuthorize("returnObject.username == authentication.name")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserResponse updateUser(String userId, UserUpdateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		userMapper.updateUser(user, request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		Role role = Role.builder()
				.name("USER")
				.description("user role")
				.build();
		
		user.setRoles(new HashSet<>(Arrays.asList(role)));

		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserResponse adminUpdateUser(String userId, AdminUpdateRequest request) {
		User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

		userMapper.AdminUpdateUser(user, request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		var roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));

		return userMapper.toUserResponse(userRepository.save(user));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(String userId) {
		userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		userRepository.deleteById(userId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserResponse> getUsers(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable).stream().map(userMapper::toUserResponse).toList();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserResponse getUser(String userId) {
		return userMapper.toUserResponse(
				userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<UserResponse> getUserByKeyword(String keyword) {
		List<UserResponse> userResponsesPhone = userRepository.findByPhone(keyword).stream().map(userMapper::toUserResponse).toList();
		List<UserResponse> userResponsesName = userRepository.findByName(keyword).stream().map(userMapper::toUserResponse).toList();
		List<UserResponse> userResponsesEmail = userRepository.findByEmail(keyword).stream().map(userMapper::toUserResponse).toList();
		
		Set<UserResponse> combinedUserResponsesSet = new HashSet<>();
		combinedUserResponsesSet.addAll(userResponsesPhone);
		combinedUserResponsesSet.addAll(userResponsesName);
		combinedUserResponsesSet.addAll(userResponsesEmail);

		// Chuyển đổi lại thành List nếu cần
		List<UserResponse> userResponses = new ArrayList<>(combinedUserResponsesSet);
		
		return userResponses;
		
		
	}
	
	public PageRespose getPage(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> userPage = userRepository.findAll(pageable);
		return pageMapper.toPageResponse(userPage);
	}
	
	
}
