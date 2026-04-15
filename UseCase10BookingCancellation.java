import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Goal: Use a Stack to track allocated IDs and perform LIFO rollbacks.
 */

// 1. Service to manage Cancellation and Rollback
class CancellationService {
    private Map<String, Integer> inventory;
    // Stack tracks Room IDs for LIFO rollback behavior
    private Stack<String> allocationStack = new Stack<>();
    private Map<String, String> roomToTypeMap = new HashMap<>();

    public CancellationService(Map<String, Integer> inventory) {
        this.inventory = inventory;
    }

    // Records an allocation so it can be rolled back later
    public void trackAllocation(String roomId, String roomType) {
        allocationStack.push(roomId);
        roomToTypeMap.put(roomId, roomType);
        System.out.println("[Tracked] Room " + roomId + " added to rollback stack.");
    }

    // Performs the actual rollback/cancellation
    public void processCancellation() {
        if (allocationStack.isEmpty()) {
            System.out.println("[Error] No active bookings to cancel.");
            return;
        }

        // LIFO: Get the most recent room ID
        String roomIdToCancel = allocationStack.pop();
        String type = roomToTypeMap.get(roomIdToCancel);

        // Inventory Rollback: Increment the count
        inventory.put(type, inventory.get(type) + 1);

        System.out.println("[ROLLBACK] Cancelled Room: " + roomIdToCancel);
        System.out.println("[SYSTEM] Inventory for " + type + " restored to " + inventory.get(type));
    }
}

// 2. Main Application Class
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Inventory Rollback (v10.0) ===");
        System.out.println("------------------------------------------------");

        // Initial State
        Map<String, Integer> hotelInventory = new HashMap<>();
        hotelInventory.put("Suite", 5);
        
        CancellationService cancelService = new CancellationService(hotelInventory);

        // 1. Simulate two bookings
        System.out.println("Initial Suites: " + hotelInventory.get("Suite"));
        
        // Booking 1
        hotelInventory.put("Suite", 4);
        cancelService.trackAllocation("SUI-101", "Suite");
        
        // Booking 2
        hotelInventory.put("Suite", 3);
        cancelService.trackAllocation("SUI-102", "Suite");

        System.out.println("Current Suites: " + hotelInventory.get("Suite"));

        // 2. Perform Rollback (Undo the most recent booking)
        System.out.println("\nInitiating Cancellation (Undo)...");
        cancelService.processCancellation();

        // 3. Perform another Rollback
        System.out.println("\nInitiating second Cancellation...");
        cancelService.processCancellation();

        System.out.println("\n------------------------------------------------");
        System.out.println("Application Terminated Safely.");
    }
}
