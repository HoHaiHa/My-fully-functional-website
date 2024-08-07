package springbootWeb2.com.hohaiha.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.PermissionRequest;
import springbootWeb2.com.hohaiha.app.dto.response.PermissionResponse;


@Service
public interface PermissionService {
    public PermissionResponse create(PermissionRequest request);

    public List<PermissionResponse> getAll();

    public void delete(String permission);
}
