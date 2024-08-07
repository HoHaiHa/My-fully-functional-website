package springbootWeb2.com.hohaiha.app.mapper;


import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.entity.Permission;

@Component
public interface PermissionMapper {
    public Permission toPermission(PermissionRequest request);

    public PermissionResponse toPermissionResponse(Permission permission);
}
