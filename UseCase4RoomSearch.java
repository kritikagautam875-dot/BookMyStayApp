import java.util.HashMap;
import java.util.Map;

// Abstract class representing a Room
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
    public double getPrice() { return price; }
    
    public void displayInfo() {
        System.out.print("Room Type: " + type + " | Beds: " + beds + " | Price: $" + price);
    }
}

class SingleRoom extends Room { public SingleRoom() { super("Single", 1, 100.0); } }
class DoubleRoom extends Room { public DoubleRoom() { super("Double", 2, 180.0); } }
class SuiteRoom  extends Room { public SuiteRoom()  { super("Suite", 3, 350.0); } }

// Centralized Inventory (from Use Case 3)
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory); // Returns a copy for safe read-only access
    }
}

/**
 * Use Case 4: Search Service
 * Handles read-only operations to prevent state mutation.
 */
class SearchService {
    private RoomInventory inventory;
    private Map<String, Room> roomTemplates;

    public SearchService(RoomInventory inventory, Map<String, Room> roomTemplates) {
        this.inventory = inventory;
        this.roomTemplates = roomTemplates;
    }

    public void searchAvailableRooms() {
        System.out.println("\n--- Searching for Available Rooms ---");
        boolean found = false;

        for (String type : roomTemplates.keySet()) {
            int availableCount = inventory.getAvailability(type);
            
            // Validation: Only display rooms with availability > 0
            if (availableCount > 0) {
                Room room = roomTemplates.get(type);
                room.displayInfo();
                System.out.println(" | Available: " + availableCount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No rooms currently available.");
        }
    }
}

// Main Application Class
public class UseCase4RoomSearch {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Room Search Service (v4.0) ===");

        // 1. Initialize Inventory
        RoomInventory hotelInventory = new RoomInventory();
        hotelInventory.addRoomType("Single", 5);
        hotelInventory.addRoomType("Double", 0); // Out of stock for testing
        hotelInventory.addRoomType("Suite", 2);

        // 2. Map room types to objects for search details
        Map<String, Room> roomTemplates = new HashMap<>();
        roomTemplates.put("Single", new SingleRoom());
        roomTemplates.put("Double", new DoubleRoom());
        roomTemplates.put("Suite", new SuiteRoom());

        // 3. Initialize Search Service
        SearchService searchService = new SearchService(hotelInventory, roomTemplates);

        // 4. Perform Search
        searchService.searchAvailableRooms();

        System.out.println("\nSearch Completed without modifying system state.");
        System.out.println("------------------------------------------------");
    }
}
