import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 9: Error Handling & Validation
 * Goal: Use custom exceptions and validation to guard system state.
 */

// 1. Custom Exception for Domain-Specific Errors
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// 2. Validator Service to check inputs
class BookingValidator {
    public static void validateRequest(String roomType, Map<String, Integer> inventory) 
            throws InvalidBookingException {
        
        // Check 1: Null or Empty Input
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Error: Room type cannot be empty.");
        }

        // Check 2: Invalid Room Type (Case Sensitive)
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Error: Room type '" + roomType + "' does not exist.");
        }

        // Check 3: Inventory Exhaustion
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("Error: No availability for '" + roomType + "'.");
        }
    }
}

// 3. Inventory Service with Guarded Updates
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public void processBooking(String roomType) {
        try {
            // Guard Clause: Validate before modifying state
            BookingValidator.validateRequest(roomType, inventory);

            // If validation passes, update state
            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println("[SUCCESS] Booking confirmed for: " + roomType);

        } catch (InvalidBookingException e) {
            // Graceful Failure: Print message but keep system running
            System.err.println("[VALIDATION FAILED] " + e.getMessage());
        }
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// 4. Main Application Class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Error Handling & Validation (v9.0) ===");
        System.out.println("-------------------------------------------------------");

        InventoryService hotel = new InventoryService();
        hotel.addRoomType("Single", 1);
        hotel.addRoomType("Suite", 5);

        // Scenario A: Valid Booking
        System.out.println("Attempting valid booking...");
        hotel.processBooking("Single");

        // Scenario B: Out of Stock (Fail-Fast)
        System.out.println("\nAttempting out-of-stock booking...");
        hotel.processBooking("Single");

        // Scenario C: Invalid Room Type (Case Sensitivity Check)
        System.out.println("\nAttempting invalid room type (single vs Single)...");
        hotel.processBooking("single"); 

        // Scenario D: Null/Empty input
        System.out.println("\nAttempting empty input...");
        hotel.processBooking("");

        hotel.displayInventory();
        System.out.println("-------------------------------------------------------");
        System.out.println("Application Terminated Safely.");
    }
}
