// ======================================================================
//  Control: FineController
//  Implements Strategy Pattern context — uses FineStrategy to calculate fines.
//
//  Methods: calculateFine, updateFine, markAsPaid, viewFine, setStrategy
//  Pattern: Context in Strategy Pattern
// ======================================================================

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Control: FineController
 *
 * STRATEGY PATTERN — Context:
 * - Holds a reference to a FineStrategy.
 * - Delegates fine calculation to the strategy, without caring about the rate.
 * - The strategy can be swapped at runtime:
 *     fineController.setStrategy(new StudentFineStrategy())  → 5 BDT/day
 *     fineController.setStrategy(new TeacherFineStrategy())  → 3 BDT/day
 */
public class FineController {

    // ── Strategy Pattern: strategy field ──────────────────────────────
    private FineStrategy strategy;

    private List<Fine> fines = new ArrayList<>();

    /** Default: student fine rate */
    public FineController() {
        this.strategy = new StudentFineStrategy();
    }

    /**
     * Strategy Pattern — set the fine calculation algorithm at runtime.
     * @param strategy  StudentFineStrategy or TeacherFineStrategy
     */
    public void setStrategy(FineStrategy strategy) {
        this.strategy = strategy;
        System.out.println("[FineController] Strategy set: " + strategy.getDescription());
    }

    /**
     * Calculate and store a Fine using the current strategy.
     */
    public Fine calculateFine(String fineId, BorrowRecord borrowRecord) {
        Fine fine = new Fine(fineId, borrowRecord, strategy);
        fines.add(fine);
        System.out.println("[FineController] Fine calculated: " + fine.getAmount() +
                           " BDT (" + fine.getOverdueDays() + " overdue day(s)) " +
                           "using " + strategy.getDescription());
        return fine;
    }

    public void updateFine(Fine fine, BorrowRecord borrowRecord) {
        fine.updateFine(borrowRecord, strategy);
        System.out.println("[FineController] Fine updated: " + fine.getAmount() + " BDT");
    }

    public void markAsPaid(Fine fine) {
        fine.markAsPaid();
    }

    public List<Fine> viewFine(String userId, List<BorrowRecord> borrowRecords) {
        // Get all borrow IDs belonging to this user
        List<String> userBorrowIds = borrowRecords.stream()
            .filter(r -> r.getUserId().equals(userId))
            .map(BorrowRecord::getBorrowId)
            .collect(Collectors.toList());

        List<Fine> userFines = fines.stream()
            .filter(f -> userBorrowIds.contains(f.getBorrowId()))
            .collect(Collectors.toList());

        System.out.println("\n[FineController] Fines for user '" + userId + "':");
        for (Fine f : userFines) System.out.println("  " + f);
        return userFines;
    }

    public List<Fine> getFines() { return fines; }
}
