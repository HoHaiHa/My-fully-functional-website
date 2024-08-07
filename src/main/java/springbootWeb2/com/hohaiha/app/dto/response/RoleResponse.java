package springbootWeb2.com.hohaiha.app.dto.response;

import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
	private String name;
	private String description;
	private Set<PermissionResponse> permissions;
}
