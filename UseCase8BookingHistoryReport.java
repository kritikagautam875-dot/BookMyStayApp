import java.util.ArrayList;
import java.util.List;

/**
 * Use Case 8: Booking History & Reporting
 * Goal: Maintain a chronological record of confirmed bookings for audits and reporting.
 */

// 1. Confirmed Reservation Record
class ConfirmedReservation {
    private String guestName;
    private String roomType;
    private String roomId;
    private double basePrice;

    public ConfirmedReservation(String guestName, String roomType, String roomId, double basePrice) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Guest: %-10s | Room: %-8s | Price: $%.2f", 
                              roomId, guestName, roomType, basePrice);
    }
}

// 2. Booking History Service (Data Storage)
class BookingHistory {
    // List preserves insertion order for a chronological audit trail
    private List<ConfirmedReservation> history = new ArrayList<>();

    public void recordBooking(ConfirmedReservation reservation) {
        history.add(reservation);
    }

    public List<ConfirmedReservation> getHistory() {
        // Return a copy to ensure reporting does not modify stored data
        return new ArrayList<>(history);
    }
}

// 3. Reporting Service (Operational Visibility)
class ReportingService {
    private BookingHistory historyStore;

    public ReportingService(BookingHistory historyStore) {
        this.historyStore = historyStore;
    }

    public void generateSummaryReport() {
        List<ConfirmedReservation> records = historyStore.getHistory();
        
        System.out.println("\n--- Booking History Audit Report ---");
        if (records.isEmpty()) {
            System.out.println("No confirmed bookings found.");
        } else {
            records.forEach(System.out::println);
            System.out.println("------------------------------------");
            System.out.println("Total Confirmed Bookings: " + records.size());
        }
    }
}

// 4. Main Application Class
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay: History & Reporting (v8.0) ===");
        System.out.println("------------------------------------------------");

        // Initialize Services
        BookingHistory historyStore = new BookingHistory();
        ReportingService reportService = new ReportingService(historyStore);

        // 1. Simulating Booking Confirmations (Adding to History)
        System.out.println("[System] Recording confirmed bookings...");
        historyStore.recordBooking(new ConfirmedReservation("Alice", "Suite", "SUI-402", 350.0));
        historyStore.recordBooking(new ConfirmedReservation("Bob", "Single", "SIN-105", 100.0));
        historyStore.recordBooking(new ConfirmedReservation("Charlie", "Double", "DBL-201", 180.0));

        // 2. Admin requests the Report
        reportService.generateSummaryReport();

        System.out.println("\nHistorical data retained for operational audit.");
        System.out.println("------------------------------------------------");
        System.out.println("Application Terminated.");
    }
}

