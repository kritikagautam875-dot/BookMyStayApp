import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Goal: Serialize and Deserialize system state to survive application restarts.
 */

// 1. Serializable Data Model
class HotelState implements Serializable {
    private static final long serialVersionUID = 1L;
    public Map<String, Integer> inventory;
    public List<String> bookingHistory;

    public HotelState(Map<String, Integer> inventory, List<String> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}

// 2. Persistence Service
class PersistenceService {
    private static final String FILE_NAME = "hotel_data.ser";

    // Save state to file (Serialization)
    public void saveState(Map<String, Integer> inventory, List<String> history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            HotelState state = new HotelState(inventory, history);
            oos.writeObject(state);
            System.out.println("[SAVE] System state persisted to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save state: " + e.getMessage());
        }
    }

    // Load state from file (Deserialization)
    public HotelState loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[LOAD] No persistence file found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("[LOAD] Restoring system state from " + FILE_NAME + "...");
            return (HotelState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Recovery failed: " + e.getMessage());
            return null;
        }
    }
}

// 3. Main Application Class
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Persistence & Recovery (v12.0) ===");
        
        PersistenceService persistence = new PersistenceService();
        Map<String, Integer> inventory;
        List<String> history;

        // --- STEP 1: RECOVERY ---
        HotelState restored = persistence.loadState();
        if (restored != null) {
            inventory = restored.inventory;
            history = restored.bookingHistory;
        } else {
            // Initial Seed Data if no file exists
            inventory = new HashMap<>();
            inventory.put("Suite", 10);
            history = new ArrayList<>();
        }

        // --- STEP 2: OPERATION ---
        System.out.println("Current Inventory: " + inventory);
        System.out.println("Current History Size: " + history.size());

        System.out.println("\n[ACTION] Processing a new booking...");
        if (inventory.get("Suite") > 0) {
            inventory.put("Suite", inventory.get("Suite") - 1);
            history.add("Booking #" + (history.size() + 1) + " (Suite)");
        }

        // --- STEP 3: PERSISTENCE ---
        persistence.saveState(inventory, history);

        System.out.println("\nFinal State for this session: " + inventory);
        System.out.println("Run the program again to see the data persist!");
        System.out.println("----------------------------------------------------");
    }
}

