import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Goal: Manage incoming requests fairly using a Queue.
 */

// 1. Reservation Class to hold guest intent
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested: " + roomType;
    }
}

// 2. BookingRequestQueue to manage the order of requests
class BookingRequestQueue {
    // LinkedList implements the Queue interface in Java
    private Queue<Reservation> requestQueue = new LinkedList<>();

    // Add request to the back of the line (Enqueue)
    public void submitRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("[Intake] Request received: " + reservation);
    }

    // Display all requests in arrival order
    public void displayQueue() {
        System.out.println("\n--- Current Booking Queue (Arrival Order) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
    }

    // Prepare for processing (for the next Use Case)
    public Reservation getNextRequest() {
        return requestQueue.peek(); // View the first in line without removing
    }
}

// 3. Main Application Class
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: Fair Request Intake (v5.0) ===");
        System.out.println("------------------------------------------------");

        BookingRequestQueue queueManager = new BookingRequestQueue();

        // Simulating guests submitting requests at different times
        queueManager.submitRequest(new Reservation("Alice", "Suite"));
        queueManager.submitRequest(new Reservation("Bob", "Single"));
        queueManager.submitRequest(new Reservation("Charlie", "Double"));

        // Displaying the queue to prove FIFO order is preserved
        queueManager.displayQueue();

        System.out.println("\nNote: No inventory has been modified yet.");
        System.out.println("All requests are waiting in the queue for allocation.");
        System.out.println("------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}
