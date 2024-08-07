package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.response.PageRespose;
import springbootWeb2.com.hohaiha.app.entity.User;

@Component
public interface PageMapper {
	public PageRespose toPageResponse(Page<User> page);
}
