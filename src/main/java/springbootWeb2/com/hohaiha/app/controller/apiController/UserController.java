package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.AdminCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.AdminUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserCreationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.UserUpdateRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.PageRespose;
import springbootWeb2.com.hohaiha.app.dto.response.UserResponse;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping
	ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		return ApiResponse.<UserResponse>builder().result(userService.createUser(request)).build();
	}

	@PostMapping("/admincreate")
	ApiResponse<UserResponse> adminCreateUser(@RequestBody @Valid AdminCreationRequest request) {
		return ApiResponse.<UserResponse>builder().result(userService.AdminCreateUser(request)).build();
	}

	@GetMapping({"", "/search/"})
	ApiResponse<List<UserResponse>> getUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRespose pages = userService.getPage(page, size);

		return ApiResponse.<List<UserResponse>>builder().result(userService.getUsers(page, size)).page(pages).build();
	}

	@GetMapping("/{userId}")
	ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
		return ApiResponse.<UserResponse>builder().result(userService.getUser(userId)).build();
	}

	@GetMapping("/my-info")
	ApiResponse<UserResponse> getMyInfo() {
		return ApiResponse.<UserResponse>builder().result(userService.getMyInfo()).build();
	}

	@DeleteMapping("/{userId}")
	ApiResponse<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return ApiResponse.<String>builder().result("User has been deleted").build();
	}

	@PutMapping("/{userId}")
	ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
		return ApiResponse.<UserResponse>builder().result(userService.updateUser(userId, request)).build();
	}

	@PutMapping("/admin/{userId}")
	ApiResponse<UserResponse> adminUpdateUser(@PathVariable String userId, @RequestBody AdminUpdateRequest request) {
		return ApiResponse.<UserResponse>builder().result(userService.adminUpdateUser(userId, request)).build();
	}

	@GetMapping("/search/{keyword}")
	ApiResponse<List<UserResponse>> getUserByKeyword(@PathVariable String keyword) {
		return ApiResponse.<List<UserResponse>>builder().result(userService.getUserByKeyword(keyword)).build();
	}
	

}
