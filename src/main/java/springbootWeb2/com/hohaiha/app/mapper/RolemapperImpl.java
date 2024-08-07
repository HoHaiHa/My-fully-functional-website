package springbootWeb2.com.hohaiha.app.mapper;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;
import springbootWeb2.com.hohaiha.app.entity.Role;

@Component
public class RolemapperImpl implements RoleMapper {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Role toRole(RoleRequest request) {
        return Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
