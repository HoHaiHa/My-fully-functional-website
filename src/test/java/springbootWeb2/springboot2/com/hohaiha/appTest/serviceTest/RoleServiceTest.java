package springbootWeb2.springboot2.com.hohaiha.appTest.serviceTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;
import springbootWeb2.com.hohaiha.app.entity.Permission;
import springbootWeb2.com.hohaiha.app.entity.Role;
import springbootWeb2.com.hohaiha.app.mapper.RoleMapper;
import springbootWeb2.com.hohaiha.app.repository.PermissionRepository;
import springbootWeb2.com.hohaiha.app.repository.RoleRepository;
import springbootWeb2.com.hohaiha.app.service.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private RoleRequest roleRequest;
    private Role role;
    private RoleResponse roleResponse;
    private Permission permission;

    @BeforeEach
    public void setUp() {
        permission = Permission.builder()
                .name("CREATE_DATA")
                .description("Permission to create data")
                .build();

        roleRequest = RoleRequest.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(List.of("CREATE_DATA"))
                .build();

        role = Role.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(new HashSet<>(Set.of(permission)))
                .build();

        roleResponse = RoleResponse.builder()
                .name("ADMIN")
                .description("Administrator role")
                .permissions(Set.of(new PermissionResponse("CREATE_DATA", "Permission to create data")))
                .build();
    }

    // Test cho phương thức create với dữ liệu hợp lệ
    @Test
    public void testCreateRole_ValidData() {
        when(roleMapper.toRole(any(RoleRequest.class))).thenReturn(role);
        when(permissionRepository.findAllById(any(List.class))).thenReturn(List.of(permission));
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.toRoleResponse(any(Role.class))).thenReturn(roleResponse);

        RoleResponse result = roleService.create(roleRequest);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
    }

    // Test cho phương thức create với dữ liệu không hợp lệ (ví dụ: không có permission)
    @Test
    public void testCreateRole_InvalidPermissions() {
        roleRequest.setPermissions(List.of("INVALID_PERMISSION"));

        when(roleMapper.toRole(any(RoleRequest.class))).thenReturn(role);
        when(permissionRepository.findAllById(any(List.class))).thenReturn(List.of());
        when(roleRepository.save(any(Role.class))).thenReturn(role);
        when(roleMapper.toRoleResponse(any(Role.class))).thenReturn(roleResponse);

        RoleResponse result = roleService.create(roleRequest);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
    }

    // Test cho phương thức getAll
    @Test
    public void testGetAllRoles_Valid() {
        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toRoleResponse(any(Role.class))).thenReturn(roleResponse);

        List<RoleResponse> result = roleService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getName());
    }

    // Test cho phương thức delete
    @Test
    public void testDeleteRole_Valid() {
        doNothing().when(roleRepository).deleteById(any(String.class));

        roleService.delete("ADMIN");

        verify(roleRepository).deleteById("ADMIN");
    }

    // Test cho phương thức delete với role không tồn tại (giả lập ngoại lệ)
    @Test
    public void testDeleteRole_NotFound() {
        doNothing().when(roleRepository).deleteById(any(String.class));

        // Chỉ cần gọi phương thức mà không cần xác minh vì không có ngoại lệ nào được ném ra
        roleService.delete("NON_EXISTENT_ROLE");

        verify(roleRepository).deleteById("NON_EXISTENT_ROLE");
    }

    // Test cho phương thức getAll khi không có role nào
    @Test
    public void testGetAllRoles_NoRoles() {
        when(roleRepository.findAll()).thenReturn(List.of());
        
        List<RoleResponse> result = roleService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
