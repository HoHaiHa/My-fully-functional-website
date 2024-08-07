package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;
import springbootWeb2.com.hohaiha.app.entity.Permission;
@Component
public class PermissionMapperImpl implements PermissionMapper {

    @Override
    public Permission toPermission(PermissionRequest request) {
        return Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    @Override
    public PermissionResponse toPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
