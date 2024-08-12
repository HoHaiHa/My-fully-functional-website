package springbootWeb2.com.hohaiha.app.mapper;

import org.springframework.stereotype.Component;

import springbootWeb2.com.hohaiha.app.dto.request.ProductRequest;
import springbootWeb2.com.hohaiha.app.dto.response.ProductResponse;
import springbootWeb2.com.hohaiha.app.entity.Product;

@Component
public class ProductMapperImpl implements ProductMapper{

	@Override
	public Product toProduct(ProductRequest request) {
		return Product.builder()
				.name(request.getName())
				.img(request.getImg())
				.price(request.getPrice())
				.descriptions(request.getDescriptions())
				.material(request.getMaterial())
				.quantity(request.getQuantity())
				.discount(request.getDiscount())
				.feature(request.getFeature())
				.size(request.getSize())
				.brand(request.getBrand())
				.hot(request.isHot())
				.Color(request.getColor())
				.build();
	}

	@Override
	public ProductResponse toProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.img(product.getImg())
				.price(product.getPrice())
				.descriptions(product.getDescriptions())
				.material(product.getMaterial())
				.quantity(product.getQuantity())
				.discount(product.getDiscount())
				.feature(product.getFeature())
				.size(product.getSize())
				.brand(product.getBrand())
				.category(product.getCategory())
				.Color(product.getColor())
				.hot(product.isHot())
				.build();
	}

}
