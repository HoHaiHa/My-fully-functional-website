package springbootWeb2.com.hohaiha.app.configuration;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import springbootWeb2.com.hohaiha.app.entity.Cart;
import springbootWeb2.com.hohaiha.app.entity.Role;
import springbootWeb2.com.hohaiha.app.entity.User;
import springbootWeb2.com.hohaiha.app.repository.CartRepository;
import springbootWeb2.com.hohaiha.app.repository.RoleRepository;
import springbootWeb2.com.hohaiha.app.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
	 @Autowired
     private CartRepository cartRepository;
    

    @NonFinal
    private static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    private static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
    	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
            	
                roleRepository.save(Role.builder()
                        .name("USER")
                        .description("User role")
                        .build());
                
                Role adminRole = roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);
                
                Cart cart = new Cart();
                cartRepository.save(cart);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .cart(cart)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
