package springbootWeb2.com.hohaiha.app.service;

import org.springframework.stereotype.Service;

import springbootWeb2.com.hohaiha.app.dto.request.AuthenticationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.IntrospectRequest;
import springbootWeb2.com.hohaiha.app.dto.response.AuthenticationResponse;
import springbootWeb2.com.hohaiha.app.dto.response.IntrospectResponse;
import springbootWeb2.com.hohaiha.app.entity.User;

@Service
public interface AuthenticationService {
	 AuthenticationResponse authenticate(AuthenticationRequest request); 
	 public String generateToken(User user);
	 public IntrospectResponse introspect(IntrospectRequest request );
}
