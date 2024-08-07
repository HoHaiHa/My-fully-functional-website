package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.mapper.PermissionMapper;
import springbootWeb2.com.hohaiha.app.repository.PermissionRepository;
import springbootWeb2.com.hohaiha.app.entity.Permission;

@Component
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
	@Autowired
    private PermissionRepository permissionRepository;
	@Autowired
    private PermissionMapper permissionMapper;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
