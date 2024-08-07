package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;
import springbootWeb2.com.hohaiha.app.service.RoleService;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
	@Autowired
    private RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
