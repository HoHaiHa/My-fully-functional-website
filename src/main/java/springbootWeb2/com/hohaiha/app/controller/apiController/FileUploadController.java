package springbootWeb2.com.hohaiha.app.controller.apiController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springbootWeb2.com.hohaiha.app.dto.response.ApiResponse;
import springbootWeb2.com.hohaiha.app.exception.AppException;
import springbootWeb2.com.hohaiha.app.exception.ErrorCode;

@RestController
@RequestMapping("/upload")
public class FileUploadController {
	@PostMapping("/productimg")
	ApiResponse<String> uploadProductImg(@RequestParam("file") MultipartFile file) {
		String uploadDir = "src/main/resources/static/images/products/";
		String imgid = UUID.randomUUID().toString();
		String fileName = imgid+"_"+file.getOriginalFilename();
		Path filePath = Paths.get(uploadDir + fileName);
		try {
			Files.write(filePath, file.getBytes());

			// Tạo URL để truy cập file
			String fileUrl = "/images/products/" + fileName;

			return ApiResponse.<String>builder().result(fileUrl).build();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException(ErrorCode.ERROR_UPLOAD_PRODUCTFILE);
		}
	}
}
