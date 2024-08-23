package springbootWeb2.com.hohaiha.app.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
public class CloudinaryServiceImpl implements CloudinaryService {
	@Autowired
	private Cloudinary cloudinary;

	public Map uploadImage(MultipartFile file) throws IOException {
		return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
	}

	// Phương thức xóa ảnh
    public Map<String, Object> deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
