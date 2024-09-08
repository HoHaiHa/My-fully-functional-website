package springbootWeb2.com.hohaiha.app.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateRequest {
    @Size(min = 6, message = "INVALID_PASSWORD")
    private String password;
	private String name;

	private LocalDate dob;
	
	private String email;
    private String phone;

}
