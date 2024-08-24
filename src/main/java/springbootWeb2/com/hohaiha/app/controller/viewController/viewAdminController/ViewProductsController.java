package springbootWeb2.com.hohaiha.app.controller.viewController.viewAdminController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ViewProductsController {
	@GetMapping("/products")
	public String getProducts() {
		return "/admin/pages/products";
	}
	@GetMapping("/detailProduct")
	public String getProduct() {
		return "/admin/pages/detailProduct";
	}
	@GetMapping("/addProduct")
	public String addProduct() {
		return "/admin/pages/addProduct";
	}
	@GetMapping("/updateProduct")
	public String updateProduct() {
		return "/admin/pages/updateProduct";
	}
	

}
