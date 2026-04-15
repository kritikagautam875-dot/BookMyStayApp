// File name: UseCase2RoomInitialization.java

/**
 * Book My Stay App - Use Case 2 (v2.0)
 * Goal: Demonstrate Object Modeling through Inheritance and Abstraction.
 * In this version, we use a single file for simplicity in VS Code.
 */

// Abstract class representing a generalized Room
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room characteristics
    public void displayInfo() {
        System.out.print("Room Type: " + type + " | Beds: " + beds + " | Price: $" + price);
    }
}

// Concrete implementation for Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 100.0);
    }
}

// Concrete implementation for Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 180.0);
    }
}

// Concrete implementation for Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 350.0);
    }
}

// Main Application Class
public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Room Initialization (v2.0) ===");
        System.out.println("------------------------------------------------");

        // 1. Initialize Room Objects using Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // 2. Store availability using static variables (limitations of v2.0)
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // 3. Display Room details and their current state
        single.displayInfo();
        System.out.println(" | Available Units: " + singleAvailability);

        doubleRoom.displayInfo();
        System.out.println(" | Available Units: " + doubleAvailability);

        suite.displayInfo();
        System.out.println(" | Available Units: " + suiteAvailability);

        System.out.println("------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}
