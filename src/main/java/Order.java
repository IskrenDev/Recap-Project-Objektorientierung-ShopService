import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

public record Order(
        String id,
        List<Product> products,
        LocalDateTime orderTime,
        @With
        OrderStatus status
) {
}
