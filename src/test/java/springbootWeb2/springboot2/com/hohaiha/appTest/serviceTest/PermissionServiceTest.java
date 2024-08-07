package springbootWeb2.springboot2.com.hohaiha.appTest.serviceTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.entity.Permission;
import springbootWeb2.com.hohaiha.app.mapper.PermissionMapper;
import springbootWeb2.com.hohaiha.app.repository.PermissionRepository;
import springbootWeb2.com.hohaiha.app.service.PermissionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    private PermissionRequest permissionRequest;
    private Permission permission;
    private PermissionResponse permissionResponse;

    @BeforeEach
    public void setUp() {
        permissionRequest = PermissionRequest.builder()
                .name("CREATE_DATA")
                .description("Permission to create data")
                .build();

        permission = Permission.builder()
                .name("CREATE_DATA")
                .description("Permission to create data")
                .build();

        permissionResponse = PermissionResponse.builder()
                .name("CREATE_DATA")
                .description("Permission to create data")
                .build();
    }

    // Test cho phương thức create với dữ liệu hợp lệ
    @Test
    public void testCreatePermission_ValidData() {
        when(permissionMapper.toPermission(any(PermissionRequest.class))).thenReturn(permission);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);
        when(permissionMapper.toPermissionResponse(any(Permission.class))).thenReturn(permissionResponse);

        PermissionResponse result = permissionService.create(permissionRequest);

        assertNotNull(result);
        assertEquals("CREATE_DATA", result.getName());
    }

    // Test cho phương thức create với dữ liệu không hợp lệ
    @Test
    public void testCreatePermission_InvalidData() {
        permissionRequest.setName(""); // Tên không hợp lệ

        when(permissionMapper.toPermission(any(PermissionRequest.class))).thenReturn(permission);
        when(permissionRepository.save(any(Permission.class))).thenReturn(permission);
        when(permissionMapper.toPermissionResponse(any(Permission.class))).thenReturn(permissionResponse);

        PermissionResponse result = permissionService.create(permissionRequest);

        assertNotNull(result);
    }

    // Test cho phương thức getAll
    @Test
    public void testGetAllPermissions_Valid() {
        when(permissionRepository.findAll()).thenReturn(List.of(permission));
        when(permissionMapper.toPermissionResponse(any(Permission.class))).thenReturn(permissionResponse);

        List<PermissionResponse> result = permissionService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("CREATE_DATA", result.get(0).getName());
    }

    // Test cho phương thức delete
    @Test
    public void testDeletePermission_Valid() {
        doNothing().when(permissionRepository).deleteById(any(String.class));

        permissionService.delete("CREATE_DATA");

        verify(permissionRepository).deleteById("CREATE_DATA");
    }

    // Test cho phương thức delete với permission không tồn tại (giả lập ngoại lệ)
    @Test
    public void testDeletePermission_NotFound() {
        doNothing().when(permissionRepository).deleteById(any(String.class));

        // Chỉ cần gọi phương thức mà không cần xác minh vì không có ngoại lệ nào được ném ra
        permissionService.delete("NON_EXISTENT_PERMISSION");

        verify(permissionRepository).deleteById("NON_EXISTENT_PERMISSION");
    }

    // Test cho phương thức getAll khi không có permission nào
    @Test
    public void testGetAllPermissions_NoPermissions() {
        when(permissionRepository.findAll()).thenReturn(List.of());
        
        List<PermissionResponse> result = permissionService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}

