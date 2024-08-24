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
				.color(request.getColor())
				.creationDate(request.getCreationDate())
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
				.color(product.getColor())
				.hot(product.isHot())
				.creationDate(product.getCreationDate())
				.build();
	}

	@Override
	public Product toProductFromUpdate(Product product, ProductRequest request) {
		product.setName(request.getName());
		product.setImg(request.getImg());
		product.setPrice(request.getPrice());
		product.setDescriptions(request.getDescriptions());
		product.setMaterial(request.getMaterial());
		product.setQuantity(request.getQuantity());
		product.setDescriptions(request.getDescriptions());
		product.setFeature(request.getFeature());
		product.setSize(request.getSize());
		product.setBrand(request.getBrand());
		product.setColor(request.getColor());
		product.setHot(request.isHot());
		product.setCreationDate(request.getCreationDate() );
		return product;
	}

	

}
