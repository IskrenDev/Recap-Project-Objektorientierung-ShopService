import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

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

        Order newOrder = new Order(UUID.randomUUID().toString(), products, OrderStatus.PROCESSING);

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
