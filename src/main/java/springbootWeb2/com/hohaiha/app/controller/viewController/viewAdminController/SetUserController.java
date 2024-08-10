package springbootWeb2.com.hohaiha.app.controller.viewController.viewAdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class SetUserController {
	@GetMapping("/getusers")
	public String getUsers() {
		return "/admin/pages/users";
	}
	
	@GetMapping("/adduser")
	public String addUsers() {
		return "/admin/pages/addUser";
	}
	
	@GetMapping("/detailuser")
	public String detail() {
		return "/admin/pages/detailUser";
	}
	
	@GetMapping("/updateuser")
	public String updateuser() {
		return "/admin/pages/updateUser";
	}
}
