package springbootWeb2.com.hohaiha.app.dto.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class PageRespose {
    private int number;
    private int totalPages;
    private long totalElements;
    private int size;
    private boolean isFirst;
    private boolean isLast;
    private int numberOfElements;
}
