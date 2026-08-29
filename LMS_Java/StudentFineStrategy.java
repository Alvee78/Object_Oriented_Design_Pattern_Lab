// ======================================================================
//  Strategy Pattern — Concrete Strategy A
//  StudentFineStrategy: 5 BDT per overdue day
// ======================================================================

/**
 * Strategy Pattern — Concrete Strategy A
 * Student fine: 5 BDT per overdue day.
 */
public class StudentFineStrategy implements FineStrategy {

    private static final double RATE_PER_DAY = 5.0; // BDT

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * RATE_PER_DAY;
    }

    @Override
    public String getDescription() {
        return "Student Fine Strategy: " + RATE_PER_DAY + " BDT/day";
    }
}
