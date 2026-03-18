import controller.RideSharingController;
import models.TripStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RideSharingController controller = RideSharingController.getInstance();

        System.out.println("========== STEP 1: CREATE USERS ==========");
        controller.createUser("U1", "Alice");
        controller.createUser("U2", "Bob");

        System.out.println("\n========== STEP 2: CREATE RIDER ==========");
        controller.createRider(
                "R1",
                "RiderOne",
                "V1",
                "Honda City",
                "WB-01-1234",
                "FOUR_WHEELER",
                Arrays.asList("BASIC", "PREMIUM")
        );

        // Set rider location near source
        controller.updateRiderLocation("R1", 22.5726, 88.3639);

        System.out.println("\n========== STEP 3: COMPLETE BOOKING FLOW ==========");
        String fareId1 = "F1";

        var estimates1 = controller.getFareEstimate(
                "U1",
                fareId1,
                22.5726, 88.3639,
                22.5800, 88.3700
        );

        System.out.println("Fare Estimates: " + estimates1);

        HashMap<String, String> trip1 = controller.requestTrip(
                "U1",
                fareId1 + ":1",
                "T1",
                "1234"
        );

        System.out.println("Trip Created: " + trip1);

        controller.updateTripStatus("R1", "T1", TripStatus.STARTED, "1234");
        Thread.sleep(1000); // simulate time passing
        HashMap<String, String> completedTrip =
                controller.updateTripStatus("R1", "T1", TripStatus.ENDED, "1234");

        System.out.println("Trip Completed: " + completedTrip);

        System.out.println("\n========== STEP 4: FARE EXPIRY FLOW ==========");
        String fareId2 = "F2";

        controller.getFareEstimate(
                "U1",
                fareId2,
                22.5726, 88.3639,
                22.5900, 88.4000
        );

        // Simulate expiry (depends on your internal expiry logic)
        Thread.sleep(2000); // assume fare expires quickly

        HashMap<String, String> expiredTripAttempt = controller.requestTrip(
                "U1",
                fareId2 + ":2",
                "T2",
                "9999"
        );

        System.out.println("Expired Fare Booking Attempt: " + expiredTripAttempt);

        System.out.println("\n========== STEP 5: TWO USERS SAME RIDER CONFLICT ==========");
        String fareId3 = "F3";
        String fareId4 = "F4";

        controller.getFareEstimate(
                "U1",
                fareId3,
                22.5726, 88.3639,
                22.5800, 88.3700
        );

        controller.getFareEstimate(
                "U2",
                fareId4,
                22.5726, 88.3639,
                22.5800, 88.3700
        );

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(() -> {
            // First user books successfully
            HashMap<String, String> tripUser1 = controller.requestTrip(
                    "U1",
                    fareId3 + ":1",
                    "T3",
                    "1111"
            );

            System.out.println("User1 Trip: " + tripUser1);
        });

        service.execute(() -> {
            // Second user tries booking same rider
            HashMap<String, String> tripUser2 = controller.requestTrip(
                    "U2",
                    fareId4 + ":1",
                    "T4",
                    "2222"
            );

            System.out.println("User2 Trip Attempt (should fail): " + tripUser2);
        });

    }
}