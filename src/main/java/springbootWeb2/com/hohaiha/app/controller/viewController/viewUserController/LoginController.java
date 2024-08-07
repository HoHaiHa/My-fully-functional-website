package springbootWeb2.com.hohaiha.app.controller.viewController.viewUserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String getUsers() {
		return "/user/pages/login";
	}
}
