package springbootWeb2.com.hohaiha.app.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private int totalQuantity;
	private int discount;
	private Date creationDate;
	private int totalPrice;
	private int Tax;
	private int finalTotalPrice;
	private String Address;
	private String shippingCode;
	private String notes;
	private String paymentMethod;
	private String status;
	@OneToMany
	private List<Item> item;
	@ManyToOne
	private User user;
}
