package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;
import springbootWeb2.com.hohaiha.app.entity.Role;
@Component
public interface RoleMapper {
    public Role toRole(RoleRequest request);

    public RoleResponse toRoleResponse(Role Role);
}
