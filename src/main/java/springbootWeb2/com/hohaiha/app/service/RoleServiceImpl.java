package springbootWeb2.com.hohaiha.app.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;
import springbootWeb2.com.hohaiha.app.mapper.RoleMapper;
import springbootWeb2.com.hohaiha.app.repository.PermissionRepository;
import springbootWeb2.com.hohaiha.app.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PermissionRepository permissionRepository;
	@Autowired
    private RoleMapper roleMapper;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
