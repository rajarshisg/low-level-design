import controller.FoodDeliveryAppController;
import model.Location;
import repository.*;
import service.*;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        // ------------------ Repositories ------------------
        UserRepository userRepo = new UserRepository();
        RestaurantRepository restaurantRepo = new RestaurantRepository();
        OrderRepository orderRepo = new OrderRepository();
        DeliveryPartnerRepository partnerRepo = new DeliveryPartnerRepository();

        // ------------------ Services ------------------
        UserService userService = new UserService(userRepo);
        RestaurantService restaurantService = new RestaurantService(restaurantRepo);
        DeliveryPartnerService partnerService = new DeliveryPartnerService(partnerRepo);
        OrderService orderService =
                new OrderService(orderRepo, userService, restaurantService, partnerService);

        // ------------------ Controller ------------------
        FoodDeliveryAppController controller =
                new FoodDeliveryAppController(userService, restaurantService, orderService, partnerService);

        // ------------------ Setup Phase ------------------

        // 1. Register User
        controller.registerUser("U1", "Rajarshi");

        // 2. Register Restaurant
        controller.registerRestaurant("R1", "Italian House");

        // 3. Add Food + Inventory
        controller.addFood("R1", "Biryani", 200, 10);
        controller.addFood("R1", "Kebab", 150, 5);

        // 4. Register Delivery Partners (IMPORTANT FIX)
        partnerService.registerPartner("P1", "Rider1");
        partnerService.registerPartner("P2", "Rider2");

        partnerService.updateLocation("P1", 10, 10);
        partnerService.updateLocation("P2", 12, 12);

        // ------------------ Order Flow ------------------

        // 5. Create Order
        HashMap<String, Integer> items = new HashMap<>();
        items.put("Biryani", 2);
        items.put("Kebab", 1);

        controller.createOrder("O1", "U1", "R1", items);

        // 6. Restaurant processes order
        controller.processOrder("R1");

        // 7. Assign Delivery Partner
        controller.assignPartner("O1");

        // 8. Mark order prepared (simulate kitchen completion)
        controller.markOrderPrepared("R1", "O1");

        // 9. Deliver Order
        controller.markDelivered("O1");

        // ------------------ Edge Case Testing ------------------
        // Try assigning partner again (should not reassign ideally)
        controller.assignPartner("O1");
    }
}