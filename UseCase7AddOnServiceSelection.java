import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Goal: Use Map and List to handle optional services per reservation.
 */

// 1. Simple Service Class
class AddOnService {
    String name;
    double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

// 2. Manager to handle One-to-Many relationship (Reservation ID -> List of Services)
class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationAddOns = new HashMap<>();

    public void addService(String reservationId, AddOnService service) {
        // If reservationId not present, create a new list, then add service
        reservationAddOns.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("[Service Added] " + service.name + " linked to ID: " + reservationId);
    }

    public double calculateTotalAddOnCost(String reservationId) {
        List<AddOnService> services = reservationAddOns.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService s : services) {
            total += s.price;
        }
        return total;
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = reservationAddOns.get(reservationId);
        if (services != null) {
            System.out.println("Services for " + reservationId + ": " + services);
        }
    }
}

// 3. Main Application Class
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Add-On Service Selection (v7.0) ===");
        System.out.println("-----------------------------------------------------");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Sample Add-Ons
        AddOnService breakfast = new AddOnService("Breakfast Buffet", 15.0);
        AddOnService wifi = new AddOnService("Premium WiFi", 10.0);
        AddOnService spa = new AddOnService("Spa Treatment", 50.0);

        // Assume Alice has Reservation ID: RES-101
        String resIdAlice = "RES-101";
        System.out.println("Processing Add-Ons for " + resIdAlice + "...");
        
        serviceManager.addService(resIdAlice, breakfast);
        serviceManager.addService(resIdAlice, spa);

        // Assume Bob has Reservation ID: RES-102
        String resIdBob = "RES-102";
        serviceManager.addService(resIdBob, wifi);

        // Calculate and Display
        System.out.println("\n--- Add-On Billing Summary ---");
        serviceManager.displayServices(resIdAlice);
        System.out.println("Total Add-On Cost for Alice: $" + serviceManager.calculateTotalAddOnCost(resIdAlice));

        System.out.println("\n");
        serviceManager.displayServices(resIdBob);
        System.out.println("Total Add-On Cost for Bob: $" + serviceManager.calculateTotalAddOnCost(resIdBob));

        System.out.println("-----------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}
