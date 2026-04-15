import java.util.HashMap;
import java.util.Map;

// Abstract class from Use Case 2
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() { return type; }
    
    public void displayInfo() {
        System.out.print("Room Type: " + type + " | Beds: " + beds + " | Price: $" + price);
    }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 1, 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 2, 180.0); } }
class SuiteRoom  extends Room { public SuiteRoom()  { super("Suite", 3, 350.0); } }

/**
 * Use Case 3: Room Inventory Management
 * Encapsulates availability logic using a HashMap.
 */
class RoomInventory {
    // HashMap provides O(1) lookup and centralized state
    private Map<String, Integer> inventory = new HashMap<>();

    // Initialize inventory with room types and counts
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Retrieve availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (e.g., after a booking)
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        }
    }

    public void displayInventory() {
        System.out.println("--- Current Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

// Main Application Class
public class UseCase3InventorySetup {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Centralized Inventory (v3.0) ===");

        // 1. Setup Inventory
        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.addRoomType("Single", 5);
        hotelInventory.addRoomType("Double", 3);
        hotelInventory.addRoomType("Suite", 2);

        // 2. Initialize Room Objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // 3. Display Initial State
        single.displayInfo();
        System.out.println(" | Units: " + hotelInventory.getAvailability(single.getType()));

        doubleRoom.displayInfo();
        System.out.println(" | Units: " + hotelInventory.getAvailability(doubleRoom.getType()));

        suite.displayInfo();
        System.out.println(" | Units: " + hotelInventory.getAvailability(suite.getType()));

        // 4. Demonstrate a "Booking" (State Update)
        System.out.println("\n[Action] Booking 1 Single Room...");
        hotelInventory.updateAvailability("Single", 4);

        // 5. Final Inventory Check
        hotelInventory.displayInventory();
        
        System.out.println("------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}
