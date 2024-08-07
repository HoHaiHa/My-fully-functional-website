package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.service.PermissionService;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
