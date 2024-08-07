package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.RoleRequest;
import springbootWeb2.com.hohaiha.app.dto.response.RoleResponse;

@Service
public interface RoleService {
    public RoleResponse create(RoleRequest request);

    public List<RoleResponse> getAll();

    public void delete(String role);
}
