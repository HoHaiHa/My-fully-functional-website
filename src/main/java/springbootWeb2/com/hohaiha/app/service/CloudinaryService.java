package springbootWeb2.com.hohaiha.app.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryService {
	public Map uploadImage(MultipartFile file) throws IOException;

	public Map<String, Object> deleteImage(String publicId) throws IOException;
}
