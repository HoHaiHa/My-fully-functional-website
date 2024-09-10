package springbootWeb2.com.hohaiha.app.controller.viewController.viewAdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewOrdersController {
	@GetMapping("/orders")
	public String getOrders() {
		return "/admin/pages/orders";
	}
	@GetMapping("/addOrders")
	public String addOrders() {
		return "/admin/pages/addOrders";
	}
}
