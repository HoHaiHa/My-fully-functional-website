package springbootWeb2.com.hohaiha.app.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.service.CloudinaryService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private CloudinaryService cloudinaryService;

	@SuppressWarnings("unchecked")
	@PostMapping
	public ApiResponse<Map<String, Object>>  uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
		return ApiResponse.<Map<String,Object>>builder()
				.result(cloudinaryService.uploadImage(file))
				.build();
	}

	// Điểm cuối xóa ảnh
	@DeleteMapping("/{publicId}")
	public ApiResponse deleteImage(@PathVariable String publicId) throws IOException {
		return new ApiResponse();
	}
}
