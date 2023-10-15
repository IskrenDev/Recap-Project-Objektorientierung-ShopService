import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")), LocalDateTime.now(), OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_throwProductNotFoundException() {
        // GIVEN
        ShopService shopService = new ShopService();
        List<String> productIds = List.of("1", "2");

        //WHEN + THEN
        IllegalArgumentException actual = assertThrows(IllegalArgumentException.class,
                () -> shopService.addOrder(productIds));
    }
}