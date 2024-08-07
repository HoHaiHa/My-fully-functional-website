package springbootWeb2.com.hohaiha.app.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Supplier;

import javax.security.auth.Subject;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import lombok.experimental.NonFinal;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTClaimsSet.Builder;

import springbootWeb2.com.hohaiha.app.dto.request.AuthenticationRequest;
import springbootWeb2.com.hohaiha.app.dto.request.IntrospectRequest;
import springbootWeb2.com.hohaiha.app.dto.response.AuthenticationResponse;
import springbootWeb2.com.hohaiha.app.dto.response.IntrospectResponse;
import springbootWeb2.com.hohaiha.app.repository.UserRepository;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;


@Component
public class AuthenticationServiceImpl implements AuthenticationService{
	@Autowired
	private UserRepository userRepository;
	
	@NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;
    
	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
		boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
		
		if(!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);
		
		return AuthenticationResponse.builder()
				.token(generateToken(user))
				.authenticated(authenticated)
				.build();
	}
	
	public String generateToken(User user) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		JWTClaimsSet jwsClaimsSet = new JWTClaimsSet.Builder()
				.subject(user.getUsername())
				.issuer("hohaiha")
				.issueTime(new Date())
				.expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
				.jwtID(UUID.randomUUID().toString())
				.claim("scope", buildScope(user))
				.build();
		
		Payload payload = new Payload(jwsClaimsSet.toJSONObject());
		
		JWSObject jwsObject = new JWSObject(header, payload);
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		}
		catch (Exception e) {
			System.out.println("Cannot create token" + e);
            throw new RuntimeException(e);
		}
		
				
	}
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
    
    public IntrospectResponse introspect(IntrospectRequest request ) {
    	String token = request.getToken();
    	boolean valid = true;
    	
    	try {
			verifyToken(token);
		} catch (Exception e) {
			valid = false;
		}
    	
    	return IntrospectResponse.builder()
    			.valid(valid)
    			.build();
    }
    
    public SignedJWT verifyToken(String Token) throws ParseException, JOSEException {
    	JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
    	
    	SignedJWT signedJWT = SignedJWT.parse(Token);
    	
    	var verified = signedJWT.verify(verifier);
    	
    	Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
    	
    	if(!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);
    	
    	return signedJWT;
    	
    }
}
