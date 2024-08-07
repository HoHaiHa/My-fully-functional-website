package springbootWeb2.com.hohaiha.app.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springbootWeb2.com.hohaiha.app.dto.request.AuthenticationRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	private String token;
	private boolean authenticated;
}
