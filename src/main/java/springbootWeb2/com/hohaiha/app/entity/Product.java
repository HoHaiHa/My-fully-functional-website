package springbootWeb2.com.hohaiha.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(name = "name", unique = true, columnDefinition = "VARCHAR(500) COLLATE utf8mb4_unicode_ci")
	private String name;
	private String img;
	private long price;
	private String descriptions;
	private String material;
	private int quantity;
	private int discount;
	private String feature;
	private String size;
	private String brand;
	
	@ManyToOne
	private Category category;
	
	private String Color;
	
	private boolean hot;
	
}
