import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Goal: Safely allocate rooms and prevent double-booking using Sets.
 */

// 1. Reservation Model
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// 2. Enhanced Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();
    // Use a Set to ensure unique room IDs and prevent double-booking
    private Set<String> allocatedRoomIds = new HashSet<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public String allocateRoom(String type) {
        if (isAvailable(type)) {
            // Logic: Decrement inventory
            inventory.put(type, inventory.get(type) - 1);
            
            // Logic: Generate unique Room ID (e.g., Suite-101)
            String roomId = type + "-" + (100 + new Random().nextInt(900));
            
            // Set handles uniqueness enforcement
            allocatedRoomIds.add(roomId);
            return roomId;
        }
        return null;
    }

    public void displayStatus() {
        System.out.println("\n--- Final Inventory State ---");
        inventory.forEach((type, count) -> System.out.println(type + ": " + count + " remaining"));
        System.out.println("Allocated Room IDs: " + allocatedRoomIds);
    }
}

// 3. Booking Service to process the Queue
class BookingService {
    private InventoryService inventoryService;
    private Queue<Reservation> requestQueue;

    public BookingService(InventoryService inventoryService, Queue<Reservation> requestQueue) {
        this.inventoryService = inventoryService;
        this.requestQueue = requestQueue;
    }

    public void processBookings() {
        System.out.println("\n--- Processing Room Allocations ---");
        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll(); // Dequeue (FIFO)
            
            String roomId = inventoryService.allocateRoom(request.roomType);
            
            if (roomId != null) {
                System.out.println("[SUCCESS] Confirmed: " + request.guestName + 
                                   " assigned to " + roomId);
            } else {
                System.out.println("[FAILED] No availability for " + request.guestName + 
                                   " (" + request.roomType + ")");
            }
        }
    }
}

// 4. Main Application Class
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Room Allocation Service (v6.0) ===");

        // Setup Inventory
        InventoryService inventory = new InventoryService();
        inventory.addRoomType("Single", 1); // Only 1 available
        inventory.addRoomType("Suite", 2);

        // Setup Request Queue (FIFO)
        Queue<Reservation> queue = new LinkedList<>();
        queue.add(new Reservation("Alice", "Single"));
        queue.add(new Reservation("Bob", "Single")); // This should fail
        queue.add(new Reservation("Charlie", "Suite"));

        // Run Booking Service
        BookingService bookingService = new BookingService(inventory, queue);
        bookingService.processBookings();

        // Final Verification
        inventory.displayStatus();
        System.out.println("\n------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}
