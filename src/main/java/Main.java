import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductRepo productRepo = new ProductRepo();
        OrderRepo orderRepo = new OrderListRepo();
        ShopService shopService = new ShopService(productRepo, orderRepo);

        List<String> productIds = new ArrayList<>();

        Order order1 = new Order("1", null, LocalDateTime.now(), OrderStatus.PROCESSING);
        Order order2 = new Order("2", null, LocalDateTime.now(), OrderStatus.PROCESSING);
        Order order3 = new Order("3", null, LocalDateTime.now(), OrderStatus.PROCESSING);

        productIds.add("1");
        productIds.add("2");
        productIds.add("3");

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);
        orderRepo.addOrder(order3);

        shopService.addOrder(productIds);
    }
}
