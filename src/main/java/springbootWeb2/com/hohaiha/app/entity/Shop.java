package springbootWeb2.com.hohaiha.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Shop {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String phone;
	private String address;
	private String img;
	private String status;
}
