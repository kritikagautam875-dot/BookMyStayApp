import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 * Goal: Use synchronization to prevent race conditions during simultaneous bookings.
 */

// 1. Thread-Safe Inventory Service
class ConcurrentInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    // The 'synchronized' keyword ensures only one thread executes this at a time
    public synchronized boolean bookRoom(String guestName, String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        
        if (available > 0) {
            // Simulate processing time to increase the chance of a race condition
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            inventory.put(roomType, available - 1);
            System.out.println("[SUCCESS] " + guestName + " booked a " + roomType + 
                               ". Remaining: " + (available - 1));
            return true;
        } else {
            System.out.println("[FAILED] " + guestName + " could not book " + roomType + " (Sold Out)");
            return false;
        }
    }
}

// 2. Booking Task for Threads
class BookingTask implements Runnable {
    private ConcurrentInventory inventory;
    private String guestName;
    private String roomType;

    public BookingTask(ConcurrentInventory inventory, String guestName, String roomType) {
        this.inventory = inventory;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        inventory.bookRoom(guestName, roomType);
    }
}

// 3. Main Application Class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Book My Stay: Thread Safety Simulation (v11.0) ===");
        System.out.println("------------------------------------------------------");

        ConcurrentInventory hotel = new ConcurrentInventory();
        // Only 1 Single Room available for 3 people!
        hotel.addRoomType("Single", 1); 

        System.out.println("Initial Inventory: Single Room (1)");
        System.out.println("Simulating 3 simultaneous booking requests...\n");

        // Create 3 threads trying to book the SAME single room
        Thread t1 = new Thread(new BookingTask(hotel, "Alice", "Single"));
        Thread t2 = new Thread(new BookingTask(hotel, "Bob", "Single"));
        Thread t3 = new Thread(new BookingTask(hotel, "Charlie", "Single"));

        // Start all threads at roughly the same time
        t1.start();
        t2.start();
        t3.start();

        // Wait for all threads to finish
        t1.join();
        t2.join();
        t3.join();

        System.out.println("\n------------------------------------------------------");
        System.out.println("Simulation Finished. Inventory state remains consistent.");
    }
}
