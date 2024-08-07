package springbootWeb2.com.hohaiha.app.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {
	private String name;
	private String description;
}
