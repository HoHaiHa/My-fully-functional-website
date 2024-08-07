package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.response.PageRespose;
import springbootWeb2.com.hohaiha.app.entity.User;

@Component
public class PageMapperImpl implements PageMapper {

	@Override
	public PageRespose toPageResponse(Page<User> page) {
		return PageRespose.builder()
				.isLast(page.isLast())
				.totalElements(page.getTotalElements())
				.size(page.getSize())
				.number(page.getNumber())
				.isFirst(page.isFirst())
				.totalPages(page.getTotalPages())
				.numberOfElements(page.getNumberOfElements())
				.build();
	}
	
}
