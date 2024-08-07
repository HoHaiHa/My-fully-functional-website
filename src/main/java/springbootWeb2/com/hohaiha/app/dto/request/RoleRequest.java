package springbootWeb2.com.hohaiha.app.dto.request;

import java.util.List;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {
	private String name;
	private String description;
	private List<String> permissions;
}
