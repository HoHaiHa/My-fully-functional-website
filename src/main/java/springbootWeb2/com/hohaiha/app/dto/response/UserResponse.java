package springbootWeb2.com.hohaiha.app.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	private String id;
	private String username;
	private String name;
	private LocalDate dob;
	private String email;
    private String phone;
	private Set<RoleResponse> roles;
	private String cartId;
}
