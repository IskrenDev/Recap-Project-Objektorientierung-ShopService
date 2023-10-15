import lombok.RequiredArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@RequiredArgsConstructor
public class ShopService {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            if (productToOrder.isEmpty()) {
                String errorMessage = "Product mit der Id: " + productId + " konnte nicht bestellt werden!";
                throw new IllegalArgumentException(errorMessage);
            }
            productToOrder.ifPresent(products::add);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, LocalDateTime.now(ZoneId.of("UTC")), OrderStatus.PROCESSING);

        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepo.getOrders()
                .stream()
                .filter(order -> order.status().equals(status))
                .collect(Collectors.toList());
    }


    public Order updateOrder(String id, OrderStatus status) {
        Order order = orderRepo.getOrderById(id);
        if (order != null) {
            Order updatedOrder = order.withStatus(status);
            orderRepo.updateOrder(updatedOrder);
        } else {
            System.out.println("Bestellung nicht gefunden: " + id);
        }
        return order;
    }
}
