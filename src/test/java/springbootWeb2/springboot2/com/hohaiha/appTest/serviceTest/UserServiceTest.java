package springbootWeb2.springboot2.com.hohaiha.appTest.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.Role;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;
import springbootWeb2.com.hohaiha.app.mapper.UserMapper;
import springbootWeb2.com.hohaiha.app.mapper.UserMapperImpl;
import springbootWeb2.com.hohaiha.app.repository.RoleRepository;
import springbootWeb2.com.hohaiha.app.repository.UserRepository;
import springbootWeb2.com.hohaiha.app.service.UserServiceImpl;

@Slf4j
@TestPropertySource("/test.properties")
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserMapperImpl userMapperImpl;

	@InjectMocks
	private UserServiceImpl userServiceIpml;

	private UserCreationRequest request;
	private UserResponse response;
	private User user;
	private LocalDate dob;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@BeforeEach
	void initData() {
		dob = LocalDate.of(2002, 9, 9);
		request = UserCreationRequest.builder().username("hohaiha").name("hai ha")
				.password("123456").dob(dob).build();

		response = UserResponse.builder().id("123456").username("hohaiha").name("hai ha").dob(dob)
				.build();

		user = userMapperImpl.toUser(request);
	}

	// test case with valid input
	@Test
	void testCreateUser_valid() {
		// Arrange
		Role role = new Role();
		role.setName("ROLE_USER");

		when(userMapper.toUser(request)).thenReturn(user);
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(userMapper.toUserResponse(any(User.class))).thenReturn(response);

		// Act
		UserResponse result = userServiceIpml.createUser(request);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo(request.getUsername());
		assertThat(result.getName()).isEqualTo(request.getName());
		assertThat(result.getDob()).isEqualTo(request.getDob());
	}

	// test case with invalid input (user existed throws exception)
	@Test
	void TestCreateUser_UserExisted() {
		Role role = new Role();
		role.setName("ROLE_USER");

		when(userMapper.toUser(request)).thenReturn(user);
		when(userRepository.save(any(User.class)))
				.thenThrow(new DataIntegrityViolationException("User already exists"));

		// Act & Assert
		AppException exception = assertThrows(AppException.class, () -> userServiceIpml.createUser(request));
		assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_EXISTED);
	}

	// test case valid input
	@Test
	void testUpdateUser_valid() {
		// Prepare test data
		String userId = "123";
		UserUpdateRequest request = new UserUpdateRequest();
		request.setName("NewFirstName");
		request.setPassword("123456");

		User existingUser = new User();
		existingUser.setId(userId);
		existingUser.setName("OldFirstName");
		existingUser.setPassword("123456");

		User updatedUser = new User();
		updatedUser.setId(userId);
		updatedUser.setName(request.getName());
		updatedUser.setPassword("123456");
		
		UserResponse expectedResponse = new UserResponse();
		expectedResponse.setId(userId);
		expectedResponse.setName(request.getName());

		// Define mock behavior
		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
		when(userRepository.save(existingUser)).thenReturn(updatedUser);
		when(userMapper.toUserResponse(updatedUser)).thenReturn(expectedResponse);
		when(userMapper.updateUser(existingUser,request)).thenReturn(updatedUser);
		
		// Call the method to test
		UserResponse actualResponse = userServiceIpml.updateUser(userId, request);

		// Verify the results
		assertThat(actualResponse).isNotNull();
		assertThat(actualResponse.getName()).isEqualTo(request.getName());

		// Verify mock interactions
		verify(userRepository).findById(userId);
		verify(userMapper).updateUser(existingUser, request);
		verify(userRepository).save(existingUser);
		verify(userMapper).toUserResponse(updatedUser);
	}

	// test case with invalid input (user not found)
	@Test
	void testUpdateUser_userNotFound() {
		// Prepare test data
		String userId = "123";
		UserUpdateRequest request = new UserUpdateRequest();

		// Define mock behavior
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		// Call the method to test and expect an exception
		AppException exception = assertThrows(AppException.class, () -> userServiceIpml.updateUser(userId, request));
		assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED);

		// Verify mock interactions
		verify(userRepository).findById(userId);
		verifyNoMoreInteractions(userRepository, userMapper, roleRepository);
	}

	// test case with valid input (valid Id)
	@Test
	void TestDeleteUser_valid() {
		String userId = "123";
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		userRepository.deleteById(userId);
		verify(userRepository).deleteById(userId);
	}

	// test case with invalid input (invalid Id)
	@Test
	void testDeleteUser_invalidId() {
		String userId = "123";

		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		AppException exception = assertThrows(AppException.class, () -> userServiceIpml.deleteUser(userId));
		assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED);

		verify(userRepository, never()).deleteById(userId);
	}

	// test get user by id with valid input(valid Id)
	@Test
	void testGetUserById_valid() {
		String userId = "123";

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.toUserResponse(user)).thenReturn(response);

		userServiceIpml.getUser(userId);

		verify(userRepository).findById(userId);
		verify(userMapper).toUserResponse(user);
	}

	// test get user by id with invalid input(have not match user)
	@Test
	void testGetUserById_invalidId() {
		String userId = "123";

		when(userRepository.findById(userId)).thenReturn(Optional.empty());
		when(userMapper.toUserResponse(user)).thenReturn(response);

		AppException exception = assertThrows(AppException.class, () -> userServiceIpml.getUser(userId));
		assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_EXISTED);

		verify(userRepository).findById(userId);
		verify(userMapper, never()).toUserResponse(user);
	}

}
